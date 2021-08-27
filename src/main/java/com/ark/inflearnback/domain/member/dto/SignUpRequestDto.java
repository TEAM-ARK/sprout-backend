package com.ark.inflearnback.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class SignUpRequestDto {
    @Email
    @NotNull(message = "이메일을 입력하세요.")
    private final String email;

    @NotNull(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^((?!.*[\\s])(?=.*[A-Z])(?=.*\\d).{8,15})", message = "비밀번호는 공백 없이 영문/숫자/특수문자를 조합해 8~15자리여야 합니다.")
    private final String password;

    private SignUpRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static SignUpRequestDto of(final String email, final String password) {
        return new SignUpRequestDto(email, password);
    }
}
