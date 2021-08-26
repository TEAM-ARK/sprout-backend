package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.RoleHierarchies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleHierarchiesRepository extends JpaRepository<RoleHierarchies, Long> {
    List<RoleHierarchies> findRoleHierarchiesByOrders(final int orders);
}
