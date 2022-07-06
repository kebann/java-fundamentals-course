package com.bobocode.missingqualifiers;

import com.bobocode.annotation.Bean;

import java.io.InputStream;

@Bean
public class JsonParser implements Parser {

    @Override
    public void parse(InputStream stream) {
        System.out.println("Parsing JSON");
    }
}
