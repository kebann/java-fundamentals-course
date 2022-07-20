package com.bobocode.nasa.controller;

import com.bobocode.nasa.model.NasaQueryParams;
import com.bobocode.nasa.service.ReactiveNasaPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v2/photos")
@RequiredArgsConstructor
public class ReactiveNasaPhotoController {

    @Value("${nasa.default.api.key}")
    private String defaultApiKey;
    private final ReactiveNasaPhotoService pictureService;

    @GetMapping(value = "/{sol}/largest", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> getLargestPhoto(@PathVariable("sol") Integer sol,
                                        @RequestParam(required = false) String apiKey) {
        var queryParams = new NasaQueryParams()
                .sol(sol)
                .apiKey(StringUtils.hasText(apiKey) ? apiKey : defaultApiKey);

        return pictureService.findLargestPhoto(queryParams);
    }
}
