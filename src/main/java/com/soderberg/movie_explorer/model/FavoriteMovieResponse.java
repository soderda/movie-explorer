package com.soderberg.movie_explorer.model;

import java.util.List;

public class FavoriteMovieResponse {
    private List<FavoriteMovieDto> movies;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public FavoriteMovieResponse(List<FavoriteMovieDto> movies, int currentPage, int totalPages, long totalItems) {
        this.movies = movies;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<FavoriteMovieDto> getMovies() {
        return movies;
    }

    public void setMovies(List<FavoriteMovieDto> movies) {
        this.movies = movies;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
