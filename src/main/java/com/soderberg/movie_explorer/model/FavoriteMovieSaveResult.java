package com.soderberg.movie_explorer.model;

public class FavoriteMovieSaveResult {
    private Long movieId;
    private FavoriteMovieDto savedMovie;
    private FavoriteMovieResultStatus status;

    public FavoriteMovieSaveResult(Long movieId, FavoriteMovieDto savedMovie, FavoriteMovieResultStatus status) {
        this.movieId = movieId;
        this.savedMovie = savedMovie;
        this.status = status;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public FavoriteMovieDto getSavedMovie() {
        return savedMovie;
    }

    public void setSavedMovie(FavoriteMovieDto savedMovie) {
        this.savedMovie = savedMovie;
    }

    public FavoriteMovieResultStatus getStatus() {
        return status;
    }

    public void setStatus(FavoriteMovieResultStatus status) {
        this.status = status;
    }
}
