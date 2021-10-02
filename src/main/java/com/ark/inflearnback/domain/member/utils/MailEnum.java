package com.ark.inflearnback.domain.member.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MailEnum {

    FROM_ADDRESS("lsj8367@naver.com"),
    AUTHENTICATION_TITLE("비밀번호 찾기 인증 메일입니다.");

    private String value;
}
