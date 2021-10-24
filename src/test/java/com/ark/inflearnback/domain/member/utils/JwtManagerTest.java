package com.ark.inflearnback.domain.member.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import io.jsonwebtoken.Claims;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtManagerTest {

    @Test
    @DisplayName("토큰 정상적으로 생성")
    void createToken() {
        JwtManager jwtManager = new JwtManager("ark-infrean-backend-find-password", 60 * 30);

        String email = "test@abc.com";
        String accessToken = jwtManager.generateAccessToken(email);

        Claims claims = jwtManager.tokenValidation(accessToken);
        System.out.println(claims);

        assertThat(email).isEqualTo(jwtManager.getEmail(accessToken));
    }

    @Test
    @DisplayName("토큰 유효시간 만료")
    void expiredToken() throws Exception {
        JwtManager jwtManager = new JwtManager("token-test-find-password-expired", 1);
        String email = "test@abc.com";
        String accessToken = jwtManager.generateAccessToken(email);

        Thread.sleep(2000);

        assertThatThrownBy(() -> {
            jwtManager.tokenValidation(accessToken);
        }).isInstanceOf(InvalidParameterException.class);
    }
}