package com.ark.inflearnback.domain.security.dto;

import com.ark.inflearnback.domain.security.model.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MemberAuthenticationContext extends User {
    private final Member member;

    private MemberAuthenticationContext(final Member member, final Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
    }

    public static MemberAuthenticationContext of(final Member member, final Collection<? extends GrantedAuthority> authorities) {
        return new MemberAuthenticationContext(member, authorities);
    }

    public String getPassword() {
        return member.getPassword();
    }
}
