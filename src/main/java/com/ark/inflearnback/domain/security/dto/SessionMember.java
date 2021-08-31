package com.ark.inflearnback.domain.security.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private final String email;

    public SessionMember(final String email) {
        this.email = email;
    }
}
