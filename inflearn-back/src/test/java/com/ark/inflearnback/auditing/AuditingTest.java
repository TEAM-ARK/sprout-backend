package com.ark.inflearnback.auditing;

import com.ark.inflearnback.domain.user.User;
import com.ark.inflearnback.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class AuditingTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("auditing이 적용되는지 생성일을 출력")
    void jpaAuditing() {
        User user = User.builder()
                .name("민철이")
                .email("smc5236@naver.com")
                .password("1234")
                .isSubscribed(true)
                .build();

        userRepository.save(user);

        Optional<User> result = userRepository.findById(user.getId());
        User findUser = result.get();
        System.out.println("findUser.getCreatedDate = " + findUser.getCreatedDate());
    }
}
