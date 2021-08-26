package com.ark.inflearnback.domain.security.model;

import com.ark.inflearnback.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {
    private String username;
    private String password;

    @JoinColumn(referencedColumnName = "authority")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Role role;

    private Member(final String username, final String password, final Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Builder
    public static Member of(final String username, final String password, final Role role) {
        return new Member(username, password, role);
    }
}
