package com.ark.inflearnback.configuration.security.model.entity;

import com.ark.inflearnback.domain.AbstractEntity;
import com.ark.inflearnback.domain.member.model.form.SignForm;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "type")
    private Role role;

    private String socialId;

    private String registrationId;

    private boolean isSocial;

    private Member(final String email, final String password, final Role role, final String socialId, final String registrationId, final boolean isSocial) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.socialId = socialId;
        this.registrationId = registrationId;
        this.isSocial = isSocial;
    }

    @Builder
    public static Member of(final String email, final String password, final Role role, final String socialId, final String registrationId, final boolean isSocial) {
        return new Member(email, password, role, socialId, registrationId, isSocial);
    }

    public static Member of(final SignForm request, final Role role, final String socialId, final String registrationId, final boolean isSocial) {
        return new Member(request.getEmail(), request.getPassword(), role, socialId, registrationId, isSocial);
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority((role.get())));
    }

    public UserDetails toUserDetails() {
        return User.builder()
            .username(email)
            .password(password)
            .authorities(getGrantedAuthorities())
            .build();
    }

}
