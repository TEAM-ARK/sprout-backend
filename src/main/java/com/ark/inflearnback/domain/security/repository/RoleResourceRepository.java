package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Resource;
import com.ark.inflearnback.domain.security.model.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {
    List<RoleResource> findByResource(final Resource resource);
}
