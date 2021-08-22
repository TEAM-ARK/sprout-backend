package com.ark.inflearnback.domain.user;

import com.ark.inflearnback.exception.DuplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("동일한 이메일이 존재하면 Exception 발생")
    void duplicationTest() {
        userRepository.save(new User("민철", "test@naver.com", "1234", true));
        assertThrows(DuplicationException.class, () ->{
            userService.checkDuplication("test@naver.com");
        });
    }

}