package com.soderberg.movie_explorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soderberg.movie_explorer.model.tmdb.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
    
}
