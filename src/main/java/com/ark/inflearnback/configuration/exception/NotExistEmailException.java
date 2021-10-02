package com.ark.inflearnback.configuration.exception;

public class NotExistEmailException extends RuntimeException {

    public NotExistEmailException() {
        super();
    }

    public NotExistEmailException(String message) {
        super(message);
    }

}
