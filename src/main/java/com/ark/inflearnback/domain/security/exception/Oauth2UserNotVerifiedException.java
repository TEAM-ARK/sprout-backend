package com.ark.inflearnback.domain.security.exception;

public class Oauth2UserNotVerifiedException extends RuntimeException {

    public Oauth2UserNotVerifiedException() {
    }

    public Oauth2UserNotVerifiedException(final String message) {
        super(message);
    }

}
