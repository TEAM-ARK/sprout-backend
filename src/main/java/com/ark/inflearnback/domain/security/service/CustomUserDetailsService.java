package com.ark.inflearnback.domain.security.service;

import com.ark.inflearnback.domain.security.dto.MemberAuthenticationContext;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email not found !"));
        return MemberAuthenticationContext.of(member, getGrantedAuthorities(member));
    }

    private List<GrantedAuthority> getGrantedAuthorities(final Member member) {
        final List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().getAuthority()));
        return roles;
    }
}
