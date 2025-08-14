package com.soderberg.movie_explorer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.soderberg.movie_explorer.model.FavoriteMovie;

public interface MovieRepository extends JpaRepository<FavoriteMovie, Long>, JpaSpecificationExecutor<FavoriteMovie> {
    Optional<FavoriteMovie> findByMovieId(Long movieId);
    boolean existsByMovieId(Long movieId);
    void deleteByMovieId(Long movieId);
}
