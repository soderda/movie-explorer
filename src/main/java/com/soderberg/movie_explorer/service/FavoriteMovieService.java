package com.soderberg.movie_explorer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.soderberg.movie_explorer.exception.AlreadyExistsException;
import com.soderberg.movie_explorer.exception.ResourceNotFoundException;
import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.FavoriteMovieDto;
import com.soderberg.movie_explorer.model.FavoriteMovieFilter;
import com.soderberg.movie_explorer.model.FavoriteMovieRequest;
import com.soderberg.movie_explorer.model.FavoriteMovieResponse;
import com.soderberg.movie_explorer.model.FavoriteMovieResultStatus;
import com.soderberg.movie_explorer.model.FavoriteMovieSaveResult;
import com.soderberg.movie_explorer.model.UserFavoriteMovie;
import com.soderberg.movie_explorer.model.UserFavoriteMovieId;
import com.soderberg.movie_explorer.model.UserFavoriteMovieSpecification;
import com.soderberg.movie_explorer.model.tmdb.MovieDetails;
import com.soderberg.movie_explorer.model.util.MovieMapper;
import com.soderberg.movie_explorer.repository.MovieRepository;
import com.soderberg.movie_explorer.repository.UserFavoriteMovieRepository;

@Service
public class FavoriteMovieService {
    private static final Logger log = LoggerFactory.getLogger(FavoriteMovieService.class);
    
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;
    private final UserFavoriteMovieRepository userFavoriteMovieRepository;
    private final TMDBService tmdbService;

    @Autowired
    public FavoriteMovieService(MovieMapper movieMapper, MovieRepository movieRepository, UserFavoriteMovieRepository userFavoriteMovieRepository, TMDBService tmdbService) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.userFavoriteMovieRepository = userFavoriteMovieRepository;
        this.tmdbService = tmdbService;
    }

    public FavoriteMovieDto saveFavoriteMovie(Long userId, Long movieId) {    
        if (userFavoriteMovieRepository.existsById_UserIdAndId_MovieId(userId, movieId)) {
            throw new AlreadyExistsException("User already has this movie in favorites");
        }

        FavoriteMovie movie = movieRepository.findByMovieId(movieId).orElse(null);

        if (movie == null || movie.getUpdatedAt().isBefore(LocalDateTime.now().minusDays(1))) {
            MovieDetails details = tmdbService.fetchMovieDetails(String.valueOf(movieId));
            if (details == null) {
                throw new ResourceNotFoundException("Movie not found in TMDB");
            }

            movie = movieMapper.movieDetailsToFavoriteMovie(details);
            movie.setUpdatedAt(LocalDateTime.now());

            movie = movieRepository.save(movie);
        }
        UserFavoriteMovieId userFavoriteMovieId = new UserFavoriteMovieId(userId, movieId);
        UserFavoriteMovie link = new UserFavoriteMovie(userFavoriteMovieId, movie);
        userFavoriteMovieRepository.save(link);

        FavoriteMovieDto movieDto = movieMapper.movieToDto(movie);

        return movieDto;
    }

    public List<FavoriteMovieSaveResult> saveFavoriteMovies(Long userId, List<FavoriteMovieRequest> requests) {
        List<FavoriteMovieSaveResult> results = new ArrayList<>();
        
        for (FavoriteMovieRequest request : requests) {
            Long movieId = request.getMovieId();
            try {
                FavoriteMovieDto saved = saveFavoriteMovie(userId, movieId);
                results.add(new FavoriteMovieSaveResult(movieId, saved, FavoriteMovieResultStatus.SUCCESS));
            } catch (AlreadyExistsException e) {
                results.add(new FavoriteMovieSaveResult(movieId, null, FavoriteMovieResultStatus.ALREADY_EXISTS));
            } catch (ResourceNotFoundException e) {
                results.add(new FavoriteMovieSaveResult(movieId, null, FavoriteMovieResultStatus.NOT_FOUND));
            } catch (Exception e) {
                log.error("Unexpected error occured while saving movieId {} for userId {}: {}", movieId, userId, e.getMessage(), e);
                results.add(new FavoriteMovieSaveResult(movieId, null, FavoriteMovieResultStatus.INTERNAL_ERROR));
            }
        }

        return results;
    }

    public List<FavoriteMovieDto> getAllFavoriteMoviesSorted(Long userId) {
        List<UserFavoriteMovie> userFavorites = userFavoriteMovieRepository.findAllById_UserIdOrderByCreatedAtDesc(userId);
        List<FavoriteMovieDto> movies = userFavorites.stream()
            .map(UserFavoriteMovie::getMovie)
            .map(movieMapper::movieToDto)
            .toList();
        return movies;
    }

    public FavoriteMovieResponse getPaginatedFavoriteMovies(Long userId, int page, int size) {        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserFavoriteMovie> userFavoritesPage = userFavoriteMovieRepository.findAllById_UserId(userId, pageable);
    
        List<FavoriteMovieDto> movies = userFavoritesPage.getContent()
            .stream()
            .map(UserFavoriteMovie::getMovie)
            .map(movieMapper::movieToDto)
            .toList();
        return new FavoriteMovieResponse(movies, userFavoritesPage.getNumber(), userFavoritesPage.getTotalPages(), userFavoritesPage.getSize());
    }

    public List<FavoriteMovieDto> searchFavoriteMovies(Long userId, FavoriteMovieFilter filter) {
        Specification<UserFavoriteMovie> spec = UserFavoriteMovieSpecification.filterBy(userId, filter);
        List<FavoriteMovieDto> movies = userFavoriteMovieRepository.findAll(spec)
            .stream()
            .map(UserFavoriteMovie::getMovie)
            .map(movieMapper::movieToDto)
            .toList();
        return movies;
    }

    public List<FavoriteMovieDto> getAllFavoriteMovies(Long userId) {
        List<FavoriteMovieDto> movies = userFavoriteMovieRepository
            .findAllById_UserId(userId)
            .stream()
            .map(UserFavoriteMovie::getMovie)
            .map(movieMapper::movieToDto)
            .toList();
        return movies;
    }

    public void deleteFavoriteMovie(Long userId, Long movieId) {
        if (!userFavoriteMovieRepository.existsById_UserIdAndId_MovieId(userId, movieId)) {
            throw new ResourceNotFoundException("Favorite movie with ID " + movieId + " not found for user " + userId);
        }
        userFavoriteMovieRepository.deleteById_UserIdAndId_MovieId(userId, movieId);

        // Check if movie is still favorited by someone
        if (!userFavoriteMovieRepository.existsById_MovieId(movieId)) {
            // No one has the movie favorited, remove from database
            movieRepository.deleteByMovieId(movieId);
        }
    }
}
