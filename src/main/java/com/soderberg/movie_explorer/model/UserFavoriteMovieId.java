package com.soderberg.movie_explorer.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserFavoriteMovieId implements Serializable{
    private Long userId;
    private Long movieId;
    
    public UserFavoriteMovieId() {

    }

    public UserFavoriteMovieId(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
 
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFavoriteMovieId)) {
            return false;
        }
        UserFavoriteMovieId that = (UserFavoriteMovieId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
    

}
