package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
