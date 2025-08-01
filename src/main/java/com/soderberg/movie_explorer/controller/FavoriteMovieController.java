package com.soderberg.movie_explorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.FavoriteMovieRequest;
import com.soderberg.movie_explorer.model.tmdb.MovieDetails;
import com.soderberg.movie_explorer.service.FavoriteMovieService;
import com.soderberg.movie_explorer.service.TMDBService;


/**
 * Not commenting this class yet as it will change in the following versions when users are added etc.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteMovieController {

    private final TMDBService tmdbService;
    private final FavoriteMovieService favoriteMovieService;

    @Autowired
    public FavoriteMovieController(TMDBService tmdbService, FavoriteMovieService favoriteMovieService) {
        this.tmdbService = tmdbService;
        this.favoriteMovieService = favoriteMovieService;
    }

    @GetMapping
    public List<FavoriteMovie> getAllFavoriteMovies() {
        return favoriteMovieService.getAllFavoriteMovies();
    }

    @PostMapping
    public ResponseEntity<?> addFavoriteMovie(@RequestBody FavoriteMovieRequest favoriteMovieRequest) {
        Long movieId = favoriteMovieRequest.getMovieId();

        // Fetch movie details from TMDB api
        MovieDetails movieDetails = tmdbService.fetchMovieDetails(String.valueOf(movieId));

        if (movieDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Movie with the ID " + movieId + " not found in TheMovieDB.");
        }
        
        FavoriteMovie favoriteMovie = favoriteMovieService.saveFavoriteMovie(movieDetails);

        return ResponseEntity.ok(favoriteMovie);
    }

    @DeleteMapping("/{id}")
    public void deleteFavoriteMovie(@PathVariable Long id) {
        favoriteMovieService.deleteFavoriteMovie(id);
    }
}
