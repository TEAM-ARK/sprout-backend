package com.ark.inflearnback.configuration.security.repository;

import com.ark.inflearnback.configuration.security.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
