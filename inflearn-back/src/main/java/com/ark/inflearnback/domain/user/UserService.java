package com.ark.inflearnback.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    /** TODO : 구현해야할 회원 서비스 목록
     * 1. 회원 가입
     * 2. 비밀번호 찾기 : 인프런 가입 이메일 입력하면 해당 이메일로 비밀번호 수정 링크가 전송됨.
     */

}
