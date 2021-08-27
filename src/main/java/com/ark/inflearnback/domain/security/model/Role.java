package com.ark.inflearnback.domain.security.model;

import com.ark.inflearnback.domain.AbstractEntity;
import java.util.Objects;
import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String authority;

    private String description;

    private boolean deleted;

    private Role(final String authority, final String description, final boolean deleted) {
        this.authority = authority;
        this.description = description;
        this.deleted = deleted;
    }

    @Builder
    public static Role of(final String authority, final String description, final boolean deleted) {
        return new Role(authority, description, deleted);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        final Role role = (Role) o;
        return Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority);
    }
}
