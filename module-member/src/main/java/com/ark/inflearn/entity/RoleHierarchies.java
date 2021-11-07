package com.ark.inflearn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleHierarchies extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String authority;

    private int orders;

    private boolean deleted;

    private RoleHierarchies(final String authority, final int orders, final boolean deleted) {
        this.authority = authority;
        this.orders = orders;
        this.deleted = deleted;
    }

    @Builder
    public static RoleHierarchies of(final String authority, final int orders, final boolean deleted) {
        return new RoleHierarchies(authority, orders, deleted);
    }

}
