package com.bobocode.nasa.model;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.LinkedMultiValueMap;

@Getter
public final class NasaQueryParams extends LinkedMultiValueMap<String, String> {

    private Integer sol;
    private String apiKey;

    public NasaQueryParams sol(@NonNull Integer sol) {
        this.sol = sol;
        add("sol", String.valueOf(sol));
        return this;
    }

    public NasaQueryParams apiKey(@NonNull String apiKey) {
        this.apiKey = apiKey;
        add("api_key", apiKey);
        return this;
    }
}