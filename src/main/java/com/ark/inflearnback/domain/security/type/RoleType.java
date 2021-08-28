package com.ark.inflearnback.domain.security.type;

public enum RoleType {
    ROLE_SYS_ADMIN("ROLE_SYS_ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER");

    private final String role;

    RoleType(final String role) {
        this.role = role;
    }

    public String get() {
        return role;
    }
}
