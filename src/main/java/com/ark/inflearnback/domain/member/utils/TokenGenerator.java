package com.ark.inflearnback.domain.member.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    private final Map<String, Token> map = new HashMap<>();

    public String generate() {
        UUID uuid = UUID.randomUUID();
        map.put(uuid.toString(), new Token(300));
        return uuid.toString();
    }

    public boolean isExist(String apiKey) {
        return map.containsKey(apiKey);
    }

    public void cleanUp() {
        map.forEach(this::checkExpiredAndThenRemove);
    }

    private void checkExpiredAndThenRemove(String key, Token token) {
        if (token.isExpired()) {
            map.remove(key);
        }
    }

}
