package com.bobocode.missingqualifiers;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Inject;

@Bean
public class DocumentManager {
    @Inject
    private Parser parser;
}
