package com.ark.inflearnback.configuration.filter;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain) throws IOException, ServletException {
        final String contentType = request.getHeader("Content-Type");

        if (Objects.nonNull(contentType) && contentType.toLowerCase().contains("multipart/form-data")) {
            filterChain.doFilter(request, response);
        } else {
            final ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
            final ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
        }
    }

}
