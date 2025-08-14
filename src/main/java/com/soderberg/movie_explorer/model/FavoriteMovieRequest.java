package com.soderberg.movie_explorer.model;

public class FavoriteMovieRequest {
    private Long movieId;

    public FavoriteMovieRequest() {
    }
    
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
