package com.ark.inflearnback.user;

import com.ark.inflearnback.domain.user.Role;
import com.ark.inflearnback.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("빌더 패턴을 사용한 회원 객체 생성 테스트")
    void useBuilder() {
        User user = User.builder()
                .name("민철이")
                .email("smc5236@naver.com")
                .password("1234")
                .isSubscribed(true)
                .build();

        assertThat(user.getEmail()).isEqualTo("smc5236@naver.com");
        assertThat(user.getRole()).isEqualTo(Role.GENERAL_USER);
        assertThat(user.getIsSubscribed()).isTrue();
    }
}
