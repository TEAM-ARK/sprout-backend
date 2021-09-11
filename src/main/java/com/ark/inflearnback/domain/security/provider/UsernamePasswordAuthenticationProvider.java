package com.ark.inflearnback.domain.security.provider;

import com.ark.inflearnback.domain.security.dto.MemberAuthenticationContext;
import com.ark.inflearnback.domain.security.token.RestAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public final class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final MemberAuthenticationContext context = (MemberAuthenticationContext) userDetailsService.loadUserByUsername(authentication.getName());
        final String encryptedPassword = context.getPassword();
        final String enteredPassword = (String) authentication.getCredentials();

        if (!passwordEncoder.matches(enteredPassword, encryptedPassword)) {
            throw new BadCredentialsException("password not matched !");
        }

        if (isRestAuthenticationToken(authentication.getClass())) {
            return new RestAuthenticationToken(context.getUsername(), null, context.getAuthorities());
        }

        return new UsernamePasswordAuthenticationToken(context.getUsername(), null, context.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return isUsernamePasswordAuthenticationToken(authentication) || isRestAuthenticationToken(authentication);
    }

    private boolean isUsernamePasswordAuthenticationToken(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isRestAuthenticationToken(final Class<?> authentication) {
        return RestAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
