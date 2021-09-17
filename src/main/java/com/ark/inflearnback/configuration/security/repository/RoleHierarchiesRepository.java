package com.ark.inflearnback.configuration.security.repository;

import com.ark.inflearnback.configuration.security.model.entity.RoleHierarchies;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchiesRepository extends JpaRepository<RoleHierarchies, Long> {

    List<RoleHierarchies> findRoleHierarchiesByOrders(final int orders);

}
