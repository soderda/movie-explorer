package com.soderberg.movie_explorer.model;

import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.soderberg.movie_explorer.model.tmdb.Genre;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FavoriteMovieSpecification {

    private FavoriteMovieSpecification() {
        // Lock constructor since it is a utility class
    }

    public static Specification<FavoriteMovie> filterBy(FavoriteMovieFilter filter) {
        return forRoot((Root<FavoriteMovie> r) -> r, filter);
    }

    public static <R> Specification<R> forRoot(Function<? super Root<R>, ? extends From<?, FavoriteMovie>> movieFromFn, FavoriteMovieFilter filter) {
        return (root, query, cb) -> {
            From<?, FavoriteMovie> movie = movieFromFn.apply(root);

            Predicate p = cb.conjunction();
            p = cb.and(p, titleContains(movie, cb, filter.getTitle()));
            p = cb.and(p, adultEquals(movie, cb, filter.getAdult()));
            p = cb.and(p, genreEquals(movie, cb, filter.getGenre()));
            p = cb.and(p, collectionContains(movie, cb, filter.getBelongsToCollection()));
            p = cb.and(p, voteAverageBetween(movie, cb, filter.getVoteAverageMin(), filter.getVoteAverageMax()));
            p = cb.and(p, voteCountBetween(movie, cb, filter.getVoteCountMin(), filter.getVoteCountMax()));
            p = cb.and(p, popularityBetween(movie, cb, filter.getPopularityMin(), filter.getPopularityMax()));
            p = cb.and(p, runtimeBetween(movie, cb, filter.getRuntimeMin(), filter.getRuntimeMax()));
            return p;
        };
    }

    private static Predicate titleContains(From<?, FavoriteMovie> movie, CriteriaBuilder cb, String title) {
        if (title == null || title.isBlank()) {
            return cb.conjunction();
        }
        return cb.like(cb.lower(movie.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Predicate adultEquals(From<?, FavoriteMovie> movie, CriteriaBuilder cb, Boolean adult) {
        if (adult == null) {
            return cb.conjunction();
        }
        return cb.equal(movie.get("adult"), adult);
    }

    private static Predicate genreEquals(From<?, FavoriteMovie> movie, CriteriaBuilder cb, String genre) {
        if (genre == null || genre.isBlank()) {
            return cb.conjunction();
        }
        Join<FavoriteMovie, Genre> genreJoin = movie.join("genres", JoinType.LEFT);
        return cb.equal(cb.lower(genreJoin.get("name")), genre.toLowerCase());
    }

    private static Predicate collectionContains(From<?, FavoriteMovie> movie, CriteriaBuilder cb, String collection) {
        if (collection == null || collection.isBlank()) {
            return cb.conjunction();
        }
        return cb.like(cb.lower(movie.get("belongsToCollection")), "%" + collection.toLowerCase() + "%");
    }

    private static Predicate voteAverageBetween(From<?, FavoriteMovie> movie, CriteriaBuilder cb, Double min, Double max) {
        if (min == null && max == null) {
            return cb.conjunction();
        }
        if (min != null && max!= null) {
            return cb.between(movie.get("voteAverage"), min, max);
        }
        if (min != null) {
            return cb.greaterThanOrEqualTo(movie.get("voteAverage"), min);
        }
        return cb.lessThanOrEqualTo(movie.get("voteAverage"), max);
    }

    private static Predicate voteCountBetween(From<?, FavoriteMovie> movie, CriteriaBuilder cb, Long min, Long max) {
        if (min == null && max == null) {
            return cb.conjunction();
        }
        if (min != null && max!= null) {
            return cb.between(movie.get("voteCount"), min, max);
        }
        if (min != null) {
            return cb.greaterThanOrEqualTo(movie.get("voteCount"), min);
        }
        return cb.lessThanOrEqualTo(movie.get("voteCount"), max);
    }

    private static Predicate popularityBetween(From<?, FavoriteMovie> movie, CriteriaBuilder cb, Double min, Double max) {
        if (min == null && max == null) {
            return cb.conjunction();
        }
        if (min != null && max!= null) {
            return cb.between(movie.get("popularity"), min, max);
        }
        if (min != null) {
            return cb.greaterThanOrEqualTo(movie.get("popularity"), min);
        }
        return cb.lessThanOrEqualTo(movie.get("popularity"), max);
    }

    private static Predicate runtimeBetween(From<?, FavoriteMovie> movie, CriteriaBuilder cb, Long min, Long max) {
        if (min == null && max == null) {
            return cb.conjunction();
        }
        if (min != null && max!= null) {
            return cb.between(movie.get("runtime"), min, max);
        }
        if (min != null) {
            return cb.greaterThanOrEqualTo(movie.get("runtime"), min);
        }
        return cb.lessThanOrEqualTo(movie.get("runtime"), max);
    }
}
