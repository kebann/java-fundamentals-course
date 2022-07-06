package com.bobocode.beans;

import com.bobocode.annotation.Bean;

@Bean("funnyBean")
public class JokeService {

    public void tellJoke() {
        System.out.println("My first time using an elevator was an uplifting experience. The second time let me down.");
    }
}
