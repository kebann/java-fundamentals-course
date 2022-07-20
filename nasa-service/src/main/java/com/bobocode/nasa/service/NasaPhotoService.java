package com.bobocode.nasa.service;

import com.bobocode.nasa.exception.NasaException;
import com.bobocode.nasa.model.NasaQueryParams;
import com.bobocode.nasa.model.NasaResponse;
import com.bobocode.nasa.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class NasaPhotoService {

    private final RestTemplate restTemplate;
    @Value("${nasa.base.url}")
    private String nasaBaseUrl;

    @Cacheable(value = "photos", key = "#queryParams.sol")
    public Optional<Photo> findLargestPhoto(NasaQueryParams queryParams) {
        try {
            var roverPhotos = getCuriosityRoverPhotos(queryParams);
            return roverPhotos
                    .parallelStream()
                    .map(this::enrichPhotoWithSize)
                    .max(comparing(Photo::getSize));

        } catch (Exception e) {
            throw new NasaException(e);
        }
    }

    private Photo enrichPhotoWithSize(Photo photo) {
        var headers = restTemplate.headForHeaders(photo.getUrl());
        URI location = headers.getLocation();

        if (location != null) {
            headers = restTemplate.headForHeaders(location);
            photo.setSize(headers.getContentLength());
        }

        return photo;
    }

    private List<Photo> getCuriosityRoverPhotos(NasaQueryParams queryParams) {
        String nasaUrl = UriComponentsBuilder.fromHttpUrl(nasaBaseUrl)
                .path("mars-photos/api/v1/rovers/curiosity/photos")
                .queryParams(queryParams)
                .build()
                .toUriString();

        var response = restTemplate.getForEntity(nasaUrl, NasaResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody()) {
            throw new NasaException(response.toString());
        }

        return response.getBody().getPhotos();
    }
}
