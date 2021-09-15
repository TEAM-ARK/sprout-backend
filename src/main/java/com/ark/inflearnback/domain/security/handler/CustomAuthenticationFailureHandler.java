package com.ark.inflearnback.domain.security.handler;

import com.ark.inflearnback.config.model.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFailureHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException exception)
        throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errMsg = "Email or password is invalid.";

        if (exception instanceof BadCredentialsException) {
            errMsg = "Email or password is invalid.";
        }

        objectMapper.writeValue(response.getWriter(),
            HttpResponse.of(HttpStatus.UNAUTHORIZED, errMsg));
    }

}
