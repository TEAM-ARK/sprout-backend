package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Resource;
import com.ark.inflearnback.domain.security.model.RoleResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    List<RoleResource> findByResource(final Resource resource);

}
