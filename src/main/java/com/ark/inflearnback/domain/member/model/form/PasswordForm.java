package com.ark.inflearnback.domain.member.model.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordForm {

    @NotNull(message = "비밀번호를 입력하세요.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*])(?!.*(.)\\1\\1\\1)[0-9a-zA-Z!@#$%&*]{12,32}$",
        message = "비밀번호는 공백 없이 영문/숫자/특수문자를 조합해 12~32자리여야 합니다."
    )
    private String password;

    private PasswordForm(final String password) {
        this.password = password;
    }

    public static PasswordForm of(final String password) {
        return new PasswordForm(password);
    }

    public PasswordForm encodePassword(final PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

}
