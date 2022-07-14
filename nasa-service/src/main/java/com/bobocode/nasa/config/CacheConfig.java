package com.bobocode.nasa.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;

@EnableCaching
@Configuration
@Log4j2
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheBuilder =
                Caffeine.newBuilder()
                        .evictionListener((key, value, cause) ->
                                log.debug("Evicted entry {} = {} with cause: {}", key, value, cause))
                        .maximumSize(100)
                        .expireAfterWrite(Duration.of(10, MINUTES));

        var cacheManager =
                new CaffeineCacheManager();
        cacheManager.setCaffeine(cacheBuilder);

        return cacheManager;
    }
}
