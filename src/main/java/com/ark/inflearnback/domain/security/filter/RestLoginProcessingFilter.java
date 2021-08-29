package com.ark.inflearnback.domain.security.filter;

import com.ark.inflearnback.domain.member.dto.SignRequestDto;
import com.ark.inflearnback.domain.security.token.RestAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private ObjectMapper objectMapper;

    public RestLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/v1/login"));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isRest(request)) {
            throw new IllegalStateException("It's not a REST request.");
        }

        final SignRequestDto memberDto = objectMapper.readValue(request.getReader(), SignRequestDto.class);

        if (!StringUtils.hasText(memberDto.getEmail()) || !StringUtils.hasText(memberDto.getPassword())) {
            throw new IllegalStateException("no email or password entered.");
        }

        return getAuthenticationManager().authenticate(new RestAuthenticationToken(memberDto.getEmail(), memberDto.getPassword()));
    }

    private boolean isRest(final HttpServletRequest request) {
        return "application/json".equals(request.getHeader("Content-Type"));
    }
}
