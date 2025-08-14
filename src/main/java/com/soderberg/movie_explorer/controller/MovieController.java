package com.soderberg.movie_explorer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soderberg.movie_explorer.model.tmdb.MovieDetails;
import com.soderberg.movie_explorer.service.TMDBService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Movies API",
    description = "Api routes to explore movies"
)
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final TMDBService tmdbService;

    @Autowired
    public MovieController(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }    

    @GetMapping("/popular")
    @Operation(
        summary = "Gets popular movies",
        description = "Gets movies that are currently popular",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successfully retreived popular movies"
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Internal server error when fetching popular movies"
            )
        }
    )
    public Map<String, Object> getPopularMovies() {
        return tmdbService.fetchPopularMovies();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Gets information of a specific movie",
        description = "Movie with such id has to exist",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description =  "Successfully retrieved movie details"
            ),
            @ApiResponse(
                responseCode = "500",
                description =  "Either the movie id is wrong or TheMovieDB cannot be reached"
            )
        }
    )
    public MovieDetails getMovieById(@PathVariable String id) {
        return tmdbService.fetchMovieDetails(id);
    }

}
