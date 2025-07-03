package com.soderberg.movie_explorer.model;

public class FavoriteMovieRequest {
    private Long movieId;
    private String title;

    public FavoriteMovieRequest() {

    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
