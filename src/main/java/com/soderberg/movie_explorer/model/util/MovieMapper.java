package com.soderberg.movie_explorer.model.util;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.soderberg.movie_explorer.model.FavoriteMovie;
import com.soderberg.movie_explorer.model.FavoriteMovieDto;
import com.soderberg.movie_explorer.model.tmdb.BelongsToCollection;
import com.soderberg.movie_explorer.model.tmdb.Genre;
import com.soderberg.movie_explorer.model.tmdb.MovieDetails;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "belongsToCollection", source = "belongsToCollection", qualifiedByName="collectionToNameString")
    @Mapping(target = "movieId", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FavoriteMovie movieDetailsToFavoriteMovie(MovieDetails movieDetails);

    @Mapping(target = "genres", source = "genres", qualifiedByName = "mapGenresToNames")
    FavoriteMovieDto movieToDto(FavoriteMovie movie);

    @Named("collectionToNameString")
    default String mapBelongsToCollectionName(BelongsToCollection collection) {
        return (collection != null) ? collection.getName() : null;
    }    
    
    @Named("mapGenresToNames")
    default Set<String> mapGenresToNames(Set<Genre> genres) {
        return genres.stream()
            .map(Genre::getName)
            .collect(Collectors.toSet());
    }
}
