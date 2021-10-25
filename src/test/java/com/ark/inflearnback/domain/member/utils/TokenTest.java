package com.ark.inflearnback.domain.member.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class TokenTest {

    @Test
    void expiredTest() {
        Token token = new Token(0);
        assertThat(token.isExpired()).isTrue();
    }

}