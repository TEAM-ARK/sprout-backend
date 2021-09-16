package com.ark.inflearnback.configuration.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super();
    }

    public DuplicateEmailException(final String message) {
        super(message);
    }

}
