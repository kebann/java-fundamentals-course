package com.bobocode.nasa.service;

import com.bobocode.nasa.model.NasaQueryParams;
import com.bobocode.nasa.model.NasaResponse;
import com.bobocode.nasa.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveNasaPhotoService {

    @Value("${nasa.base.url}")
    private String nasaBaseUrl;
    private final WebClient webClient;

    public Mono<byte[]> findLargestPhoto(NasaQueryParams queryParams) {
        return getCuriosityRoverPhoto(queryParams)
                .flatMap(this::enrichPictureWithSize)
                .reduce((p1, p2) -> p1.getSize() > p2.getSize() ? p1 : p2)
                .flatMap(this::getPictureContent);
    }

    private Mono<byte[]> getPictureContent(Photo photo) {
        return webClient
                .get()
                .uri(photo.getUrl())
                .retrieve()
                .bodyToMono(byte[].class);
    }

    private Mono<Photo> enrichPictureWithSize(Photo photo) {
        return webClient
                .get()
                .uri(photo.getUrl())
                .retrieve()
                .toBodilessEntity()
                .map(res -> res.getHeaders().getContentLength())
                .map(photo::setSize);
    }

    private Flux<Photo> getCuriosityRoverPhoto(NasaQueryParams queryParams) {
        String nasaUrl = UriComponentsBuilder.fromHttpUrl(nasaBaseUrl)
                .path("mars-photos/api/v1/rovers/curiosity/photos")
                .queryParams(queryParams)
                .build()
                .toUriString();

        return webClient
                .get()
                .uri(nasaUrl)
                .retrieve()
                .bodyToMono(NasaResponse.class)
                .flatMapMany(res -> Flux.fromIterable(res.getPhotos()));
    }
}
