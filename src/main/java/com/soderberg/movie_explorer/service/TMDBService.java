package com.soderberg.movie_explorer.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.soderberg.movie_explorer.model.tmdb.MovieDetails;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class TMDBService {

    private final String apiKey;

    @Value("${themoviedb.api.url}")
    private String apiUrl;

    public TMDBService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("THEMOVIEDB_API_KEY");
    }
    
    private final RestTemplate restTemplate = new RestTemplate();

    public MovieDetails fetchMovieDetails(String movieId) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/movie/" + movieId)
            .queryParam("api_key", apiKey)
            .build()
            .toUriString();
        
        try {
            ResponseEntity<MovieDetails> response = restTemplate.exchange(url, 
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MovieDetails>() {}
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        
    }

    public Map<String, Object> fetchPopularMovies() {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/movie/popular")
            .queryParam("api_key", apiKey)
            .build()
            .toUriString();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return response.getBody();
    }
}
