package com.ark.inflearnback.domain.user;

import com.ark.inflearnback.exception.DuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("동일한 이메일이 존재하면 Exception 발생")
    void duplicationTest() {
        User tester = new User("test", "test@naver.com", "1234", true);
        BDDMockito.when(userRepository.save(tester)).thenReturn(tester);

        assertThrows(DuplicationException.class, () ->{
            userService.checkDuplicateEmail("test@naver.com");
        });
    }
}