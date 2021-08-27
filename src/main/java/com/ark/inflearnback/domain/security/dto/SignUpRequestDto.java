package com.ark.inflearnback.domain.security.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class SignUpRequestDto {
    @Email
    @NotNull
    private final String email;

    @NotNull
    private final String password;

    private SignUpRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static SignUpRequestDto of(final String email, final String password) {
        return new SignUpRequestDto(email, password);
    }
}
