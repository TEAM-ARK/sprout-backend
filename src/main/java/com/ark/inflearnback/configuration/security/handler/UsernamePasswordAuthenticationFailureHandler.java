package com.ark.inflearnback.configuration.security.handler;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public UsernamePasswordAuthenticationFailureHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        response.setContentType(APPLICATION_JSON_VALUE);

        String errMsg = "Email or password is invalid.";

        if (exception instanceof BadCredentialsException) {
            errMsg = "Email or password is invalid.";
        }

        objectMapper.writeValue(response.getWriter(),
            HttpResponse.of(UNAUTHORIZED, errMsg));
    }

}
