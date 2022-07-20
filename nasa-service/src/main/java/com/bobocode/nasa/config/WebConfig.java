package com.bobocode.nasa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        var httpClient = HttpClient.create()
                .followRedirect(true);

        return WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(1_000_000))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}