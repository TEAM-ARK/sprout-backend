package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}
