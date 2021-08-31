package com.ark.inflearnback.domain.member.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(final String message) {
        super(message);
    }
}
