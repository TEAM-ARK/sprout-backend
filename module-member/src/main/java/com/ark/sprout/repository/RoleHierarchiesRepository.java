package com.ark.sprout.repository;


import com.ark.sprout.entity.RoleHierarchies;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchiesRepository extends JpaRepository<RoleHierarchies, Long> {

    List<RoleHierarchies> findRoleHierarchiesByOrders(final int orders);

}
