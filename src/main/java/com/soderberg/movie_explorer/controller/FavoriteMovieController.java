package com.soderberg.movie_explorer.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soderberg.movie_explorer.exception.ApiError;
import com.soderberg.movie_explorer.model.FavoriteMovieBatchRequest;
import com.soderberg.movie_explorer.model.FavoriteMovieDto;
import com.soderberg.movie_explorer.model.FavoriteMovieFilter;
import com.soderberg.movie_explorer.model.FavoriteMovieRequest;
import com.soderberg.movie_explorer.model.FavoriteMovieResponse;
import com.soderberg.movie_explorer.model.FavoriteMovieResultStatus;
import com.soderberg.movie_explorer.model.FavoriteMovieSaveResult;
import com.soderberg.movie_explorer.service.FavoriteMovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


/**
 * Not commenting this class yet as it will change in the following versions when users are added etc.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteMovieController {

    private final FavoriteMovieService favoriteMovieService;

    @Value("${max.favorite.batch.size:20}")
    private int maxFavoriteBatchSize;

    @Autowired
    public FavoriteMovieController(FavoriteMovieService favoriteMovieService) {
        this.favoriteMovieService = favoriteMovieService;
    }

    
    @Operation(
        summary = "Favorites one or more movies by ID",
        description = "Favorites up to 20 movies in a single request. Each movie will be processed individually, and the response will reflect the outcome for each one.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "All movies were successfully favorited",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "207", 
                description = "Some movies were successfully favorited, others failed (already favorited, not found or internal server error)",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "All movies failed to be favorited due to client-side issues (invalid movie IDs, already favorited or batch size exceeded)",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "All movies failed to be favorited due to server-side errors",
                content = @Content()
            )
        }
    )
    @PostMapping
    public ResponseEntity<?> saveFavoriteMovies(@Valid @RequestBody FavoriteMovieBatchRequest batchRequest, @RequestHeader("X-User-Id") Long userId) {
        List<FavoriteMovieRequest> favoriteMovieRequests = batchRequest.getMovies();

        if (favoriteMovieRequests.size() > maxFavoriteBatchSize) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only favorite up to " + maxFavoriteBatchSize + " movies at once.");
        }

        List<FavoriteMovieSaveResult> results = favoriteMovieService.saveFavoriteMovies(userId, favoriteMovieRequests);

        HttpStatus status;
        if (results.stream().allMatch(r -> r.getStatus().equals(FavoriteMovieResultStatus.INTERNAL_ERROR))) {
            // Only internal server error results
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (results.stream().allMatch(r -> r.getStatus().equals(FavoriteMovieResultStatus.NOT_FOUND))) {
            // Only non-successes, but no INTERNAL ERRORS, could be ALREADY_EXISTS, NOT_FOUND, etc.
            status = HttpStatus.BAD_REQUEST;
        } else if (results.stream().allMatch(r -> !r.getStatus().equals(FavoriteMovieResultStatus.SUCCESS))){
            // Some failed (could be internal server error) and some succeeded
            status = HttpStatus.MULTI_STATUS;
        } else {
            // All succeeded
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(results, status);
    }

    @Operation(
        summary = "Get favorite movies",
        description = "Returns a paginated list of favorite movies, or all favorite movies if 'all=true'",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Favorite movies returned successfully"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Failed to return movies due to internal server error",
                content = @Content(schema = @Schema(implementation = ApiError.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<FavoriteMovieResponse> getFavoriteMovies(
        @Parameter(description = "Page number (starting with 0)", example="0") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "Returns all results on one page if true", example = "false") @RequestParam(required = false) Boolean all,
        @RequestHeader("X-User-Id") Long userId)  {

        FavoriteMovieResponse response = favoriteMovieService.getFavoriteMovies(userId, page, size, all);
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Search for favorite movies",
        description = "Returns a list of favorite movies matching the given filter",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of favorite movies matching filter returned succesfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid filter parameters",
                content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Failed to search and return movies due to internal server error",
                content = @Content(schema = @Schema(implementation = ApiError.class))
            )
        }
    )
    @GetMapping("/search")
    public ResponseEntity<FavoriteMovieResponse> searchFavorites(@Valid @ParameterObject FavoriteMovieFilter filter, @RequestHeader("X-User-Id") Long userId) {
        List<FavoriteMovieDto> results = favoriteMovieService.searchFavoriteMovies(userId, filter);
        return ResponseEntity.ok(
            new FavoriteMovieResponse(
                results, 
                0, 
                1, 
                results.size(),
                results.size(),
                false,
                false
            )
        );
    }


    @Operation(
        summary = "Un-favorite a movie by ID",
        description = "Un-favorites a movie by its ID",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Movie successfully un-favorited"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Favorite movie with the given ID not found",
                content = @Content(schema = @Schema(implementation = ApiError.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Failed to un-favorite movie due to internal server error",
                content = @Content(schema = @Schema(implementation = ApiError.class))
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavoriteMovie(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        favoriteMovieService.deleteFavoriteMovie(userId, id);
        return ResponseEntity.noContent().build();
    }
}
