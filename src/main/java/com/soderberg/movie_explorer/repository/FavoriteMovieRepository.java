package com.soderberg.movie_explorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soderberg.movie_explorer.model.FavoriteMovie;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    // Will maybe add future queries here
}
