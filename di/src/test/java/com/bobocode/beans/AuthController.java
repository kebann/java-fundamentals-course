package com.bobocode.beans;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Inject;
import com.bobocode.annotation.Qualifier;
import lombok.Getter;

@Bean
@Getter
public class AuthController {
    @Inject
    private JokeService jokeService;
    @Inject
    @Qualifier("basicAuth")
    private AuthService authService;
}
