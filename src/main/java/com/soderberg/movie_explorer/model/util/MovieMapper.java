package com.soderberg.movie_explorer.model.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.tmdb.BelongsToCollection;
import com.soderberg.movie_explorer.model.tmdb.MovieDetails;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "belongsToCollection", source = "belongsToCollection", qualifiedByName="collectionToNameString")
    @Mapping(target = "favoriteId", ignore = true)
    FavoriteMovie movieDetailsToFavoriteMovie(MovieDetails movieDetails);

    @Named("collectionToNameString")
    default String mapBelongsToCollectionName(BelongsToCollection collection) {
        return (collection != null) ? collection.getName() : null;
    }
}
