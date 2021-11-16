package com.ark.sprout.handler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.ark.sprout.form.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

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
