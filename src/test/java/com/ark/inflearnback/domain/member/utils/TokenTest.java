package com.ark.inflearnback.domain.member.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class TokenTest {

    @Test
    void expiredTest() throws Exception {
        Token token = new Token(1);
        Thread.sleep(2000);
        assertThat(token.isExpired()).isTrue();
    }

}