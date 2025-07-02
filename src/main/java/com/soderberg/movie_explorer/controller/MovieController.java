package com.soderberg.movie_explorer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Movies API",
    description = "Api routes to explore movies"
)
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    
    @Value("${themoviedb.api.key}")
    private String apiKey;

    @Value("${themoviedb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/popular")
    @Operation(
        summary = "Gets popular movies",
        description = "Gets movies that are currently popular",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successfully retreived popular movies", 
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Internal server error when fetching popular movies", 
                content = @Content()
            )
        }
    )
    public Map<String, Object> getPopularMovies() {
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

    @GetMapping("/{id}")
    @Operation(
        summary = "Gets information of a specific movie",
        description = "Movie with such id has to exist",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description =  "Successfully retreived movie details",
                content = @Content()
            ),
            @ApiResponse(
                responseCode = "500",
                description =  "Either the movie id is wrong or TheMovieDB cannot be reached",
                content = @Content()
            )
        }
    )
    public Map<String, Object> getMovieById(@PathVariable String id) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/movie/" + id)
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
