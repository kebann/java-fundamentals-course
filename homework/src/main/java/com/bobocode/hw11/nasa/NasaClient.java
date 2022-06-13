package com.bobocode.hw11.nasa;

import com.bobocode.hw11.nasa.model.NasaResponse;
import com.bobocode.hw11.nasa.model.Photo;
import com.bobocode.hw11.nasa.utils.CompletableFutureUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.bobocode.hw11.nasa.handler.JacksonBodyHandler.asJson;
import static java.net.http.HttpResponse.BodyHandlers;
import static java.util.Comparator.comparingLong;

@RequiredArgsConstructor
public class NasaClient {

    private static final String API_KEY = "2vqBr3jDQYYJntQFENx7zSFIwZd3knbXoaKzTKVV";
    private static final String NASA_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=" + API_KEY;
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String HEAD = "HEAD";

    private final HttpClient httpClient;

    public Optional<Photo> findLargestPhotoAsync() {
        var futures = getPhotos()
                .stream()
                .map(this::enrichPhotoInfoWithSizeAsync)
                .toList();

        var largestPhotoFuture = CompletableFutureUtils.allOf(futures)
                .thenApply(this::getPhotoWithMaxSize);

        return largestPhotoFuture.join();
    }

    public Optional<Photo> findLargestPhoto() {
        List<Photo> photos = enrichPhotosWithSize(getPhotos());
        return getPhotoWithMaxSize(photos);
    }

    private Optional<Photo> getPhotoWithMaxSize(List<Photo> photos) {
        return photos.stream()
                .max(comparingLong(Photo::getSize));
    }

    @SneakyThrows
    public List<Photo> getPhotos() {
        var request = HttpRequest.newBuilder()
                .uri(new URI(NASA_URL))
                .GET()
                .build();

        var response = httpClient.send(request, asJson(NasaResponse.class));
        return response.body().getPhotos();
    }

    @SneakyThrows
    private CompletableFuture<Photo> enrichPhotoInfoWithSizeAsync(Photo photo) {
        HttpRequest request = createHeadRequest(photo.getImgSrc());

        return httpClient
                .sendAsync(request, BodyHandlers.discarding())
                .thenApply(resp -> extractContentLength(resp.headers()))
                .exceptionally(ex -> 0L)
                .thenApply(photo::setSize);
    }

    @SneakyThrows
    private long fetchPictureSize(String url) {
        HttpHeaders headers = httpClient
                .send(createHeadRequest(url), BodyHandlers.discarding())
                .headers();

        return extractContentLength(headers);
    }

    @SneakyThrows
    private HttpRequest createHeadRequest(String url) {
        return HttpRequest
                .newBuilder()
                .method(HEAD, HttpRequest.BodyPublishers.noBody())
                .uri(new URI(url))
                .build();
    }

    private long extractContentLength(HttpHeaders headers) {
        return headers
                .firstValueAsLong(CONTENT_LENGTH_HEADER)
                .orElse(0);
    }

    private List<Photo> enrichPhotosWithSize(List<Photo> photos) {
        return photos
                .stream()
                .map(photo -> photo.setSize(fetchPictureSize(photo.getImgSrc())))
                .toList();
    }
}
