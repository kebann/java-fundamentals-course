package com.bobocode.hw11.nasa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class Photo {

    private Integer id;
    @JsonProperty("img_src")
    private String imgSrc;
    @JsonIgnore
    private long size;
}