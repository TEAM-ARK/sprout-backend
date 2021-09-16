package com.ark.inflearnback.domain.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.hasText;
import com.ark.inflearnback.domain.member.controller.form.SignForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class UsernamePasswordLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public UsernamePasswordLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/v1/login"));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isJsonType(request)) {
            throw new IllegalStateException("It's not a application/json request.");
        }

        final SignForm memberDto = objectMapper.readValue(request.getReader(), SignForm.class);

        if (!hasText(memberDto.getEmail()) || !hasText(memberDto.getPassword())) {
            throw new IllegalStateException("no email or password entered.");
        }

        return getAuthenticationManager()
            .authenticate(new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(),
                memberDto.getPassword()
            ));
    }

    private boolean isJsonType(final HttpServletRequest request) {
        return APPLICATION_JSON_VALUE.equals(request.getHeader("Content-Type"));
    }

}
