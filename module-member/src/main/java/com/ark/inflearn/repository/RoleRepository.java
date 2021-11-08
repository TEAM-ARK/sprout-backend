package com.ark.inflearn.repository;


import com.ark.inflearn.entity.Role;
import com.ark.inflearn.type.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(final RoleType roleType);

}
