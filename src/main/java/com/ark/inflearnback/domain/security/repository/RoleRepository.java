package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Role;
import com.ark.inflearnback.domain.security.type.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(final RoleType roleType);

}
