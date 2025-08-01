package com.soderberg.movie_explorer.model;

import java.util.Set;

import com.soderberg.movie_explorer.model.tmdb.Genre;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/**
 * A class representing the model of a favorite movie.
 * Used so users can favorite movies.
 * 
 */
@Entity
public class FavoriteMovie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "favorite_movie_genres",
        joinColumns = @JoinColumn(name = "favorite_movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @Column(length = 2000)
    private String overview;

    private Boolean adult;
    private String belongsToCollection;    
    private Long id;
    private Double popularity;
    private Long runtime;
    private String title;
    private Double voteAverage;
    private Long voteCount;

    public Long getFavoriteId() {
        return favoriteId;
    }
    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }
    public Boolean getAdult() {
        return adult;
    }
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    public String getBelongsToCollection() {
        return belongsToCollection;
    }
    public void setBelongsToCollection(String belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }
    public Set<Genre> getGenres() {
        return genres;
    }
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public Double getPopularity() {
        return popularity;
    }
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
    public Long getRuntime() {
        return runtime;
    }
    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
    public Long getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
    
}
