package com.ark.inflearnback.domain.security.provider;

import com.ark.inflearnback.domain.security.dto.MemberAuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public final class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final MemberAuthenticationContext context = (MemberAuthenticationContext) userDetailsService.loadUserByUsername(authentication.getName());
        final String encryptedPassword = context.getPassword();
        final String enteredPassword = (String) authentication.getCredentials();
        verifyCredentials(encryptedPassword, enteredPassword);
        return new UsernamePasswordAuthenticationToken(context.getMember(), null, context.getAuthorities());
    }

    private void verifyCredentials(final String encryptedPassword, final String enteredPassword) {
        if (!passwordEncoder.matches(enteredPassword, encryptedPassword)) {
            throw new BadCredentialsException("password not matched !");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
