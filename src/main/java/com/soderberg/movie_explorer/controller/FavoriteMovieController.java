package com.soderberg.movie_explorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.FavoriteMovieRequest;
import com.soderberg.movie_explorer.repository.FavoriteMovieRepository;


/**
 * Not commenting this class yet as it will change in the following versions when users are added etc.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteMovieController {

    private final FavoriteMovieRepository favoriteMovieRepository;

    @Autowired
    public FavoriteMovieController(FavoriteMovieRepository favoriteMovieRepository) {
        this.favoriteMovieRepository = favoriteMovieRepository;
    }

    @GetMapping
    public List<FavoriteMovie> getAllFavorites() {
        return favoriteMovieRepository.findAll();
    }

    @PostMapping
    public FavoriteMovie addFavoriteMovie(@RequestBody FavoriteMovieRequest favoriteMovieRequest) {
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setMovieId(favoriteMovieRequest.getMovieId());
        favoriteMovie.setTitle(favoriteMovieRequest.getTitle());
        return favoriteMovieRepository.save(favoriteMovie);
    }
   

    @DeleteMapping("/{id}")
    public void deleteFavoriteMovie(@PathVariable Long id) {
        favoriteMovieRepository.deleteById(id);
    }
}
