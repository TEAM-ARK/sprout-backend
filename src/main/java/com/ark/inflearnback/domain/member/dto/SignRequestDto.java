package com.ark.inflearnback.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignRequestDto {
    @Email
    @NotNull(message = "이메일을 입력하세요.")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*])(?!.*(.)\\1\\1\\1)[0-9a-zA-Z!@#$%&*]{12,32}$", message = "비밀번호는 공백 없이 영문/숫자/특수문자를 조합해 12~32자리여야 합니다.")
    private String password;

    private SignRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static SignRequestDto of(final String email, final String password) {
        return new SignRequestDto(email, password);
    }
}