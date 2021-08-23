package com.ark.inflearnback.config.logger.http;

import com.ark.inflearnback.domain.AbstractEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
    private HttpLog(String clientIp, String httpMethod, String requestUri, String requestBody, String responseBody, String token, Integer httpStatusCode) {
        this.clientIp = clientIp;
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.token = token;
        this.httpStatusCode = httpStatusCode;
    }

    public static HttpLog of(String httpMethod, String uri, String requestBody, String ip, String token, Integer httpStatusCode) {
        return of(httpMethod, uri, requestBody, null, ip, token, httpStatusCode);
    }

    public static HttpLog of(String httpMethod, String uri, String requestBody, String responseBody, String ip,
                             String token, Integer httpStatusCode) {
        return new HttpLog(ip, httpMethod, uri, requestBody, responseBody,
                token, httpStatusCode);
    }

    public static HttpLog of(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {
        return getResponseBody(response, objectMapper)
                .map(responseBody -> of(request.getMethod(), request.getRequestURI(), getRequestBody(request, objectMapper),
                        responseBody.toString(), getClientIp(request), getToken(request), response.getStatus()))
                .orElse(of(request.getMethod(), request.getRequestURI(), getRequestBody(request, objectMapper),
                        getClientIp(request), getToken(request), response.getStatus()));
    }

    private static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private static Optional<JsonNode> getResponseBody(HttpServletResponse response, ObjectMapper objectMapper) {
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        if (Objects.nonNull(cachingResponse.getContentType()) && cachingResponse.getContentType().contains("application/json") && cachingResponse.getContentAsByteArray().length != 0) {
            try {
                return Optional.of(objectMapper.readTree(cachingResponse.getContentAsByteArray()));
            }
            catch (IOException o_O) {
            }
        }
        return Optional.empty();
    }

    private static String getRequestBody(HttpServletRequest request, ObjectMapper objectMapper) {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        if (Objects.nonNull(cachingRequest.getContentType()) && cachingRequest.getContentType().contains("application/json") && cachingRequest.getContentAsByteArray().length != 0) {
            try {
                return objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString();
            }
            catch (IOException o_O) {
            }
        }
        return "";
    }

    private static String getClientIp(HttpServletRequest request) {
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
}
