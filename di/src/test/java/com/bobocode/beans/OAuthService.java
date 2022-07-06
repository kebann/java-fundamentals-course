package com.bobocode.beans;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Qualifier;

@Bean
@Qualifier("oauth")
public class OAuthService implements AuthService {
    @Override
    public void auth() {
        System.out.println("Authenticating via OAuth protocol");
    }
}
