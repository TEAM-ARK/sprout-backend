package com.ark.inflearnback.domain.security.model;

import com.ark.inflearnback.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AbstractEntity {
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
}
