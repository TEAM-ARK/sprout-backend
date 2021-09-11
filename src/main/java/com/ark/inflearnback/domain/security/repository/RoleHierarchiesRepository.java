package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.RoleHierarchies;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchiesRepository extends JpaRepository<RoleHierarchies, Long> {

    List<RoleHierarchies> findRoleHierarchiesByOrders(final int orders);

}
