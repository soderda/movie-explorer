package com.soderberg.movie_explorer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "Filter for searching favorite movies")
public class FavoriteMovieFilter {

    @Schema(description = "Title (partial or full match)")
    @Size(max = 255)
    private String title;

    @Schema(description = "Whether the movie is for adults")
    private Boolean adult;

    @Schema(description = "Genre (exact match)")
    @Size(max = 100)
    private String genre;

    @Schema(description = "Collection name (partial or full match)")
    @Size(max = 255)
    private String belongsToCollection;

    @Schema(description = "Minimum vote average")
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double voteAverageMin;

    @Schema(description = "Maximum vote average")
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double voteAverageMax;

    @Schema(description = "Minimum vote count")
    @Min(0)
    private Long voteCountMin;

    @Schema(description = "Maximum vote count")
    @Min(0)
    private Long voteCountMax;

    @Schema(description = "Minimum popularity")
    @DecimalMin("0.0")
    private Double popularityMin;

    @Schema(description = "Maximum popularity")
    @DecimalMin("0.0")
    private Double popularityMax;

    @Schema(description = "Minimum runtime (in minutes)")
    @Min(0)
    private Long runtimeMin;

    @Schema(description = "Maximum runtime (in minutes)")
    @Min(0)
    private Long runtimeMax;

    // Getters and setters ...

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Boolean getAdult() {
        return adult;
    }
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getBelongsToCollection() {
        return belongsToCollection;
    }
    public void setBelongsToCollection(String belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }
    public Double getVoteAverageMin() {
        return voteAverageMin;
    }
    public void setVoteAverageMin(Double voteAverageMin) {
        this.voteAverageMin = voteAverageMin;
    }
    public Double getVoteAverageMax() {
        return voteAverageMax;
    }
    public void setVoteAverageMax(Double voteAverageMax) {
        this.voteAverageMax = voteAverageMax;
    }
    public Long getVoteCountMin() {
        return voteCountMin;
    }
    public void setVoteCountMin(Long voteCountMin) {
        this.voteCountMin = voteCountMin;
    }
    public Long getVoteCountMax() {
        return voteCountMax;
    }
    public void setVoteCountMax(Long voteCountMax) {
        this.voteCountMax = voteCountMax;
    }
    public Double getPopularityMin() {
        return popularityMin;
    }
    public void setPopularityMin(Double popularityMin) {
        this.popularityMin = popularityMin;
    }
    public Double getPopularityMax() {
        return popularityMax;
    }
    public void setPopularityMax(Double popularityMax) {
        this.popularityMax = popularityMax;
    }
    public Long getRuntimeMin() {
        return runtimeMin;
    }
    public void setRuntimeMin(Long runtimeMin) {
        this.runtimeMin = runtimeMin;
    }
    public Long getRuntimeMax() {
        return runtimeMax;
    }
    public void setRuntimeMax(Long runtimeMax) {
        this.runtimeMax = runtimeMax;
    }

    
}
