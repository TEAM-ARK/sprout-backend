package com.ark.inflearnback.domain.security.dto;

import com.ark.inflearnback.domain.security.model.Member;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberAuthenticationContext extends User {
    private MemberAuthenticationContext(final Member member) {
        super(member.getEmail(), member.getPassword(), member.getGrantedAuthorities());
    }

    public static MemberAuthenticationContext of(final Member member) {
        return new MemberAuthenticationContext(member);
    }
}
