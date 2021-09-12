package com.ark.inflearnback.domain.security.service;

import com.ark.inflearnback.domain.security.dto.MemberAuthenticationContext;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return MemberAuthenticationContext.of(
            memberRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email not found !")));
    }

}
