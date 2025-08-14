package com.soderberg.movie_explorer.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class ProductionCountry {

    private String name;

    @JsonProperty("iso_3166_1")
    private String iso31661;    

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
