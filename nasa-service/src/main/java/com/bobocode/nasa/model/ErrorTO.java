package com.bobocode.nasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ErrorTO {
    @JsonProperty("error_message")
    String errorMessage;
}
