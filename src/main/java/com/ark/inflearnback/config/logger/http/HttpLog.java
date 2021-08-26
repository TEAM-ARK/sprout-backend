package com.ark.inflearnback.config.logger.http;

import com.ark.inflearnback.domain.AbstractEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HttpLog extends AbstractEntity {
    private String clientIp;
    private String httpMethod;
    private String requestUri;
    private String requestBody;
    private String responseBody;
    private String token;
    private int httpStatusCode;

    @Builder(access = AccessLevel.PUBLIC)
    private HttpLog(final String clientIp, final String httpMethod, final String requestUri, final String requestBody, final String responseBody, final String token, final Integer httpStatusCode) {
        this.clientIp = clientIp;
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.token = token;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpLog of(final String httpMethod, final String uri, final String requestBody, final String ip, final String token, final Integer httpStatusCode) {
        return of(httpMethod, uri, requestBody, null, ip, token, httpStatusCode);
    }

    public static HttpLog of(final String httpMethod, final String uri, final String requestBody, final String responseBody, final String ip, final String token, final Integer httpStatusCode) {
        return new HttpLog(ip, httpMethod, uri, requestBody, responseBody, token, httpStatusCode);
    }

    public static HttpLog of(final HttpServletRequest request, final HttpServletResponse response, final ObjectMapper objectMapper) {
        return getResponseBody(response, objectMapper)
                .map(responseBody -> HttpLog.builder()
                        .httpMethod(request.getMethod())
                        .requestUri(request.getRequestURI())
                        .requestBody(getRequestBody(request, objectMapper))
                        .responseBody(responseBody.toString())
                        .clientIp(getClientIp(request))
                        .token(getToken(request))
                        .httpStatusCode(response.getStatus())
                        .build()
                )
                .orElse(HttpLog.builder()
                        .httpMethod(request.getMethod())
                        .requestUri(request.getRequestURI())
                        .requestBody(getRequestBody(request, objectMapper))
                        .clientIp(getClientIp(request))
                        .token(getToken(request))
                        .httpStatusCode(response.getStatus())
                        .build());
    }

    private static Optional<JsonNode> getResponseBody(final HttpServletResponse response, final ObjectMapper objectMapper) {
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        if (isReadableResponse(cachingResponse)) {
            return readTree(objectMapper, cachingResponse);
        }
        return Optional.empty();
    }

    private static boolean isReadableResponse(final ContentCachingResponseWrapper cachingResponse) {
        return Objects.nonNull(cachingResponse.getContentType()) && cachingResponse.getContentType().contains("application/json") && cachingResponse.getContentAsByteArray().length != 0;
    }

    private static Optional<JsonNode> readTree(final ObjectMapper objectMapper, final ContentCachingResponseWrapper cachingResponse) {
        try {
            return Optional.of(objectMapper.readTree(cachingResponse.getContentAsByteArray()));
        } catch (IOException e) {
            log.warn("ContentCachingResponseWrapper parse error! returns null. info : {}", e.getMessage());
            return Optional.empty();
        }
    }

    private static String getRequestBody(final HttpServletRequest request, final ObjectMapper objectMapper) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        if (isReadableRequest(cachingRequest)) {
            return readTree(objectMapper, cachingRequest);
        }
        return "";
    }

    private static boolean isReadableRequest(final ContentCachingRequestWrapper cachingRequest) {
        return Objects.nonNull(cachingRequest.getContentType()) && cachingRequest.getContentType().contains("application/json") && cachingRequest.getContentAsByteArray().length != 0;
    }

    private static String readTree(final ObjectMapper objectMapper, final ContentCachingRequestWrapper cachingRequest) {
        try {
            return objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString();
        } catch (IOException e) {
            log.warn("ContentCachingRequestWrapper parse error! returns null. info : {}", e.getMessage());
            return null;
        }
    }

    private static String getClientIp(final HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasLength(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    private static String getToken(final HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
