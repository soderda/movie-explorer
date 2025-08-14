package com.soderberg.movie_explorer.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class FavoriteMovieBatchRequest {

    @NotEmpty(message = "You must provide at least one movie to favorite")
    private List<FavoriteMovieRequest> movies;

    public List<FavoriteMovieRequest> getMovies() {
        return movies;
    }

    public void setMovies(List<FavoriteMovieRequest> movies) {
        this.movies = movies;
    }
}
