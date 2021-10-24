package com.ark.inflearnback.configuration.token;

import com.ark.inflearnback.domain.member.utils.TokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    public TokenGenerator tokenGenerator() {
        return new TokenGenerator();
    }

}
