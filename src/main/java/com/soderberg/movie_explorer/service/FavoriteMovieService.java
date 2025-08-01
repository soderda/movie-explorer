package com.soderberg.movie_explorer.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.tmdb.Genre;
import com.soderberg.movie_explorer.model.tmdb.MovieDetails;
import com.soderberg.movie_explorer.model.util.MovieMapper;
import com.soderberg.movie_explorer.repository.FavoriteMovieRepository;
import com.soderberg.movie_explorer.repository.GenreRepository;

@Service
public class FavoriteMovieService {
    private final MovieMapper movieMapper;
    private final GenreRepository genreRepository;
    private final FavoriteMovieRepository favoriteMovieRepository;

    @Autowired
    public FavoriteMovieService(MovieMapper moviemapper, GenreRepository genreRepository, FavoriteMovieRepository favoriteMovieRepository) {
        this.movieMapper = moviemapper;
        this.genreRepository = genreRepository;
        this.favoriteMovieRepository = favoriteMovieRepository;
    }

    public FavoriteMovie saveFavoriteMovie(MovieDetails movieDetails) {
        FavoriteMovie favoriteMovie = movieMapper.movieDetailsToFavoriteMovie(movieDetails);

        Set<Genre> genres = movieDetails.getGenres().stream()
            .map(genre -> genreRepository.findById(genre.getId())
                .orElseGet(() -> genreRepository.save(genre)))
            .collect(Collectors.toSet());
        
        favoriteMovie.setGenres(genres);
        
        return favoriteMovieRepository.save(favoriteMovie);
    }

    public List<FavoriteMovie> getAllFavoriteMovies() {
        return favoriteMovieRepository.findAll();
    }

    public void deleteFavoriteMovie(Long id) {
        favoriteMovieRepository.deleteById(id);
    }

}
