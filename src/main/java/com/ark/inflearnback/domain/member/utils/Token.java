package com.ark.inflearnback.domain.member.utils;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

public class Token {
    private final int token;
    @Getter
    private final LocalDateTime expiredTime;

    public Token(int seconds) {
        this.token = (makeToken() + getExpiredTime()).hashCode();
        this.expiredTime = setExpiredTime(seconds);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }

    private String makeToken() {
        return UUID.randomUUID().toString();
    }

    private LocalDateTime setExpiredTime(int seconds) {
        final LocalDateTime now = LocalDateTime.now();
        return now.plusSeconds(seconds);
    }

}
