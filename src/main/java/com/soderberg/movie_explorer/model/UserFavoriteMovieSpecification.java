package com.soderberg.movie_explorer.model;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;

public class UserFavoriteMovieSpecification {
    
    private UserFavoriteMovieSpecification() {
        // Lock constructor since it is a utility class
    }

    public static Specification<UserFavoriteMovie> belongsToUser(Long userId) {
        return (root, query, cb) -> {
            return cb.equal(root.get("id").get("userId"), userId);
        };
    }

    public static Specification<UserFavoriteMovie> filterBy(Long userId, FavoriteMovieFilter filter) {
        Specification<UserFavoriteMovie> byUser = belongsToUser(userId);

        Specification<UserFavoriteMovie> byMovieFields = FavoriteMovieSpecification.forRoot(root -> root.join("movie", JoinType.INNER), filter);

        return byUser.and(byMovieFields);
    }
}
