package com.movies.api_gateway.controller;

import com.movies.api_gateway.model.Rating;
import com.movies.api_gateway.model.RatingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/api/public")
@RestController
@Slf4j
public class PublicController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ratings-service-url}")
    private String ratingServiceUrl;
@PostMapping
    public ResponseEntity<Object> addRating(@RequestBody RatingRequest ratingRequest){
        Rating rating;
        try{
            rating = restTemplate.postForObject(ratingServiceUrl, ratingRequest, Rating.class);
            return ResponseEntity.ok(rating);
        }catch (HttpStatusCodeException ex) {
            log.error("error Adding rating: {}", ex.getMessage());
            return ResponseEntity.status(ex.getStatusCode()).
                    contentType(MediaType.APPLICATION_JSON).
                    body(ex.getResponseBodyAsString());
    }
}
}
