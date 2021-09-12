package com.ark.inflearnback.domain.member.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super();
    }

    public DuplicateEmailException(final String message) {
        super(message);
    }

}
