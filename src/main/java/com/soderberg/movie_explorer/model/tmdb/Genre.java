package com.soderberg.movie_explorer.model.tmdb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Genre {    
    private String name;

    @Id
    private Long id;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
