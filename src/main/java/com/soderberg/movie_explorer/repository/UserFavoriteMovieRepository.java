package com.soderberg.movie_explorer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.soderberg.movie_explorer.model.UserFavoriteMovie;
import com.soderberg.movie_explorer.model.UserFavoriteMovieId;

public interface UserFavoriteMovieRepository extends JpaRepository<UserFavoriteMovie, UserFavoriteMovieId>, JpaSpecificationExecutor<UserFavoriteMovie>{
    boolean existsById_UserIdAndId_MovieId(Long userId, Long movieId);
    boolean existsById_MovieId(Long movieId);
    Page<UserFavoriteMovie> findAllById_UserId(Long userId, Pageable pageable);
    List<UserFavoriteMovie> findAllById_UserId(Long userId);
    List<UserFavoriteMovie> findAllById_UserIdOrderByCreatedAtDesc(Long userId);
    void deleteById_UserIdAndId_MovieId(Long userId, Long id);
}
