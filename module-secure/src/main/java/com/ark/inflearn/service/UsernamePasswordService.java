package com.ark.inflearn.service;

import com.ark.inflearn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernamePasswordService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return memberRepository
            .findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("email not found !"))
            .toUserDetails();
    }

}
