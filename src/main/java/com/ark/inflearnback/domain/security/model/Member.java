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
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "authority")
    private Role role;

    private Member(final String email, final String password, final Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Builder
    public static Member of(final String email, final String password, final Role role) {
        return new Member(email, password, role);
    }

    public Member update(String email) {
        this.email = email;
        return this;
    }
}
