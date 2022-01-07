package team.ark.sprout.common.config.extension;

public enum Role {
    USER("ROLE_USER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
