package com.ark.inflearnback.config.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T responseBody;

    private HttpResponse(final String responseCode, final String responseMessage) {
        this(responseCode, responseMessage, null);
    }

    private HttpResponse(final String responseCode, final String responseMessage, final T responseBody) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseBody = responseBody;
    }

    public static <T> HttpResponse<T> of(final String responseCode, final String responseMessage) {
        return new HttpResponse<T>(responseCode, responseMessage);
    }

    public static <T> HttpResponse<T> of(final HttpStatus httpStatus, final String responseMessage) {
        return new HttpResponse<T>(parseStr(httpStatus.value()), responseMessage);
    }

    public static <T> HttpResponse<T> of(final HttpStatus httpStatus) {
        return new HttpResponse<T>(parseStr(httpStatus.value()), httpStatus.getReasonPhrase());
    }

    public static <T> HttpResponse<T> of(final String responseCode, final String responseMessage, final T responseBody) {
        return new HttpResponse<T>(responseCode, responseMessage, responseBody);
    }

    private static String parseStr(final int number) {
        return String.valueOf(number);
    }
}
