package com.soderberg.movie_explorer.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class SpokenLanguage {

    private String name;

    @JsonProperty("english_name")
    private String englishName;

    @JsonProperty("iso_639_1")
    private String iso6391;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }    
}
