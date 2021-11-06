package com.ark.inflearn.entity;

import com.ark.inflearn.type.RoleType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true, nullable = false)
    private RoleType roleType;

    private String description;

    private boolean deleted;

    private Role(final RoleType roleType, final String description, final boolean deleted) {
        this.roleType = roleType;
        this.description = description;
        this.deleted = deleted;
    }

    @Builder
    public static Role of(final RoleType roleType, final boolean deleted) {
        return new Role(roleType, roleType.getDescription(), deleted);
    }

    public String get() {
        return roleType.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Role role = (Role) o;
        return Objects.equals(roleType, role.roleType);
    }

}
