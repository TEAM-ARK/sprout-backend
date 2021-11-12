package com.ark.inflearn.repository;


import com.ark.inflearn.entity.Resource;
import com.ark.inflearn.entity.RoleResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    List<RoleResource> findByResource(final Resource resource);

}
