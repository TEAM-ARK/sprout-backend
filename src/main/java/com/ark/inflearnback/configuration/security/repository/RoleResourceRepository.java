package com.ark.inflearnback.configuration.security.repository;

import com.ark.inflearnback.configuration.security.model.entity.Resource;
import com.ark.inflearnback.configuration.security.model.entity.RoleResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    List<RoleResource> findByResource(final Resource resource);

}
