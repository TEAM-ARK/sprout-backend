package com.ark.inflearnback.domain.security.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionMember implements Serializable {

    private final String email;

    public SessionMember(final String email) {
        this.email = email;
    }

}
