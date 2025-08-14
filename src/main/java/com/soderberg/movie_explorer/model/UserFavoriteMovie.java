package com.soderberg.movie_explorer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_favorite_movie")
public class UserFavoriteMovie {

    @EmbeddedId
    private UserFavoriteMovieId id;

    @MapsId("movieId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private FavoriteMovie movie;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserFavoriteMovie() {
        
    }

    public UserFavoriteMovie(UserFavoriteMovieId id, FavoriteMovie movie) {
        this.id = id;
        this.movie = movie;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }    

    public FavoriteMovie getMovie() {
        return movie;
    }

    public void setMovie(FavoriteMovie movie) {
        this.movie = movie;
    }

    public UserFavoriteMovieId getId() {
        return id;
    }

    public void setId(UserFavoriteMovieId id) {
        this.id = id;
    }
}
