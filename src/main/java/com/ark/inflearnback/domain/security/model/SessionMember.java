package com.ark.inflearnback.domain.security.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private final String email;

    public SessionMember(final Member member) {
        this.email = member.getEmail();
    }
}
