package com.ark.inflearnback.config.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T responseBody;

    private HttpResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public static HttpResponse of(String resCode, String resMessage) {
        return new HttpResponse(resCode, resMessage);
    }
}
