package com.bobocode.beans;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Qualifier;

@Bean
@Qualifier("basicAuth")
public class BasicAuthService implements AuthService {
    @Override
    public void auth() {
        System.out.println("Authenticating via basic auth");
    }
}
