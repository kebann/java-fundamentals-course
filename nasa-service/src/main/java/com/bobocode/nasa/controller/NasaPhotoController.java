package com.bobocode.nasa.controller;

import com.bobocode.nasa.exception.NoPhotoFound;
import com.bobocode.nasa.model.NasaQueryParams;
import com.bobocode.nasa.service.NasaPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;

@RestController
@RequestMapping("/v1/photos")
@RequiredArgsConstructor
public class NasaPhotoController {

    @Value("${nasa.default.api.key}")
    private String defaultApiKey;
    private final NasaPhotoService pictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity<?> getLargestPicture(@PathVariable("sol") Integer sol,
                                               @RequestParam(required = false) String apiKey) {
        var queryParams = new NasaQueryParams()
                .sol(sol)
                .apiKey(StringUtils.hasText(apiKey) ? apiKey : defaultApiKey);

        var largestPicture = pictureService
                .findLargestPhoto(queryParams)
                .orElseThrow(() -> new NoPhotoFound("No picture found for sol=" + sol));

        return ResponseEntity
                .status(PERMANENT_REDIRECT)
                .location(URI.create(largestPicture.getUrl()))
                .build();
    }
}
