package com.ark.inflearnback.domain.security.type;

public enum RoleType {
    SYS_ADMIN("ROLE_SYS_ADMIN", "시스템 관리자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자");

    private final String role;
    private final String description;

    RoleType(final String role, final String description) {
        this.role = role;
        this.description = description;
    }

    public String get() {
        return role;
    }

    public String getDescription() {
        return description;
    }
}
