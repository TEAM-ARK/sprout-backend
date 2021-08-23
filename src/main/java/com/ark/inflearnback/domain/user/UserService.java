package com.ark.inflearnback.domain.user;

import com.ark.inflearnback.exception.DuplicationException;
import com.ark.inflearnback.web.user.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * TODO : 구현해야할 회원 서비스 목록
     * 1. 회원 가입
     * 2. 비밀번호 찾기 : 인프런 가입 이메일 입력하면 해당 이메일로 비밀번호 수정 링크가 전송됨.
     */

    @Transactional
    public void userRegistration(UserRequestDto userRequestDto) {
        if (checkDuplicateEmail(userRequestDto.getEmail())) {
            throw new DuplicationException("이미 등록된 이메일입니다.");
        }
        userRepository.save(userRequestDto.toEntity());
    }

    public boolean checkDuplicateEmail(final String email){
        return userRepository.existUserByEmail(email);
    }

}
