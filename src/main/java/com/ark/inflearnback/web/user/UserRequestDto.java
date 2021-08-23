package com.ark.inflearnback.web.user;

import com.ark.inflearnback.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "이름을 입력해주세요!")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요!")
    @Email(message = "올바른 이메일 양식을 입력해주세요!")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요!")
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9`~!@#$%^&*()\\-_+=\\\\]).{8,15}$", message = "비밀번호는 영문/숫자/특수문자를 조합해 8~15자리여야 합니다.")
    private String password;

    @NotNull(message = "구독 여부를 확인해주세요!")
    private Boolean isSubscribed;

    public User toEntity() {
        return new User(this.getName(), this.getEmail(), this.getPassword(), this.getIsSubscribed());
    }

}
