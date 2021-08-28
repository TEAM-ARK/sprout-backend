package com.ark.inflearnback.domain.security.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final Map<String ,Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;

    @Builder
    public OAuthAttributes(final Map<String, Object> attributes, final String nameAttributeKey, final String name, final String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {
        return ofGoogle(nameAttributeKey, attributes);
    }

    private static OAuthAttributes ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .role(Role.of("ROLE_USER", "사용자", false))
                .build();
    }
}
