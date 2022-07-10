package com.bobocode.nasa.controller;

import com.bobocode.nasa.exception.NoPictureFound;
import com.bobocode.nasa.model.NasaQueryParams;
import com.bobocode.nasa.service.NasaPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;

@RestController
@RequestMapping("/pictures")
@RequiredArgsConstructor
public class NasaPicturesController {

    @Value("${nasa.default.api.key}")
    private String defaultApiKey;
    private final NasaPictureService pictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity<?> getLargestPicture(@PathVariable("sol") Integer sol,
                                               @RequestParam(required = false) String apiKey) {
        var queryParams = new NasaQueryParams()
                .sol(sol)
                .apiKey(StringUtils.hasText(apiKey) ? apiKey : defaultApiKey);

        var largestPicture = pictureService
                .findLargestPicture(queryParams)
                .orElseThrow(() -> new NoPictureFound("No picture found for sol=" + sol));

        return ResponseEntity
                .status(PERMANENT_REDIRECT)
                .location(URI.create(largestPicture.getPhotoUrl()))
                .build();
    }
}
