package com.ark.inflearnback.user;

import com.ark.inflearnback.domain.user.User;
import com.ark.inflearnback.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    UserRepository userRepository;

    //    @Test
    //    @DisplayName("빌더 패턴을 사용한 회원 객체 생성 테스트")
    //    void useBuilder() {
    //        User user = User.builder()
    //                .name("민철이")
    //                .email("smc5236@naver.com")
    //                .password("1234")
    //                .isSubscribed(true)
    //                .build();
    //
    //        assertThat(user.getEmail()).isEqualTo("smc5236@naver.com");
    //        assertThat(user.getRole()).isEqualTo(Role.GENERAL_USER);
    //        assertThat(user.getIsSubscribed()).isTrue();
    //    }

    @Test
    @DisplayName("Spring Data JPA 테스트")
    void repositoryTest() {
        User user1 = new User("민철", "smc5236@naver.com", "1234", true);
        User user2 = new User("윤지", "hello@naver.com", "1234", false);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        Assertions.assertThat(userRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(userRepository.findById(savedUser1.getId()).get().getName()).isEqualTo("민철");
        Assertions.assertThat(userRepository.findById(savedUser2.getId()).get().getName()).isEqualTo("윤지");
    }
}
