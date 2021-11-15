package com.ark.sprout.repository;


import com.ark.sprout.entity.Resource;
import com.ark.sprout.entity.RoleResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    List<RoleResource> findByResource(final Resource resource);

}
