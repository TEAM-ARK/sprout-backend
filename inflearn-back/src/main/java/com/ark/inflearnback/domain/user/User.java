package com.ark.inflearnback.domain.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue
    @NotNull
    private Long id;

    @NotEmpty(message = "이름이 공백일 순 없어요!")
    private String name;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.GENERAL_USER; // 처음 회원가입할 때는 모두 일반회원으로 생성!

    private Boolean isSubscribed;
}
