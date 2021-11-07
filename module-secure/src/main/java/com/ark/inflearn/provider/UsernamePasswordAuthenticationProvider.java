package com.ark.inflearn.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public final class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        final String encryptedPassword = userDetails.getPassword();
        final String enteredPassword = (String) authentication.getCredentials();

        if (!passwordEncoder.matches(enteredPassword, encryptedPassword)) {
            throw new BadCredentialsException("password not matched !");
        }

        return new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            null, // 인증성공 시 보안상 인증토큰에서 비밀번호 정보 제거
            userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return isUsernamePasswordAuthenticationToken(authentication);
    }

    private boolean isUsernamePasswordAuthenticationToken(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
