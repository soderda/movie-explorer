package com.soderberg.movie_explorer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * A class representing the model of a favorite movie.
 * Used so users can favorite different movies.
 * 
 */
@Entity
public class FavoriteMovie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;
    private String title;

    /**
     * Creates a new {@link FavoriteMovie} instance.
     * 
     */
    public FavoriteMovie() {

    }

    /**
     * Get the internal database id of the {@link FavoriteMovie}.
     * 
     * @return id of the {@link FavoriteMovie}
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the MovieDatabaseID of the {@link FavoriteMovie}.
     * 
     * @return movieId of the {@link FavoriteMovie}
     */
    public Long getMovieId() {
        return movieId;
    }
    
    /**
     * Get the title of the {@link FavoriteMovie}.
     * 
     * @return title of the {@link FavoriteMovie}
     */
    public String getTitle() {
        return title;
    }


    /**
     * Sets the movieId of the {@link FavoriteMovie}.
     * The movieId should match TheMovieDB id.
     * 
     * @param movieId the movieId of the {@link FavoriteMovie}
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    /**
     * Sets the title of the {@link FavoriteMovie}.
     * 
     * @param title the title of the {@link FavoriteMovie}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
       return "FavoriteMovie id:" + id + " movieId:" + movieId + " title:" + title;
    }
}
