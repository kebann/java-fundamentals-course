package com.bobocode.nasa.model;

import lombok.NonNull;
import org.springframework.util.LinkedMultiValueMap;

public final class NasaQueryParams extends LinkedMultiValueMap<String, String> {

    public NasaQueryParams sol(@NonNull Integer sol) {
        add("sol", String.valueOf(sol));
        return this;
    }

    public NasaQueryParams apiKey(@NonNull String apiKey) {
        add("api_key", apiKey);
        return this;
    }
}