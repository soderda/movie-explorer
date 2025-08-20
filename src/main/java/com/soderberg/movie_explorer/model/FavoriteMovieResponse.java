package com.soderberg.movie_explorer.model;

import java.util.List;

public class FavoriteMovieResponse {
    private List<FavoriteMovieDto> movies;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public FavoriteMovieResponse(List<FavoriteMovieDto> movies, 
                                    int currentPage, 
                                    int totalPages, 
                                    long totalItems, 
                                    int pageSize, 
                                    boolean hasNextPage, 
                                    boolean hasPreviousPage) 
    {
        this.movies = movies;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }
}
