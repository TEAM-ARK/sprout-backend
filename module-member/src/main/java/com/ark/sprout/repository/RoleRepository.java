package com.ark.sprout.repository;


import com.ark.sprout.entity.Role;
import com.ark.sprout.type.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(final RoleType roleType);

}
