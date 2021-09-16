package com.ark.inflearnback.configuration.security.service;

import com.ark.inflearnback.configuration.security.model.entity.Resource;
import com.ark.inflearnback.configuration.security.model.entity.RoleHierarchies;
import com.ark.inflearnback.configuration.security.model.entity.RoleResource;
import com.ark.inflearnback.configuration.security.repository.ResourceRepository;
import com.ark.inflearnback.configuration.security.repository.RoleHierarchiesRepository;
import com.ark.inflearnback.configuration.security.repository.RoleResourceRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityResourceService {

    private static final String HIERARCHY_DELIMITER = " > ";
    private static final String HIERARCHY_JOIN_STR = " AND ";

    private static final int ORDERS_OF_SYS_ADMIN = 1;
    private static final int ORDERS_OF_ADMIN = 2;
    private static final int ORDERS_OF_USER = 3;

    private final ResourceRepository resourceRepository;
    private final RoleResourceRepository roleResourceRepository;
    private final RoleHierarchiesRepository roleHierarchiesRepository;

    @Transactional(readOnly = true)
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceList = new LinkedHashMap<>();
        final List<Resource> resources = resourceRepository.findAll();
        assemblyConfigAttribute(resourceList, resources);
        return loggingAuthorizationTable(resourceList);
    }

    private void assemblyConfigAttribute(final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceList, final List<Resource> resources) {
        for (Resource resource : resources) {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            assemblyConfigAttribute(resourceList, resource, configAttributes);
        }
    }

    private void assemblyConfigAttribute(final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceList, final Resource resource, final List<ConfigAttribute> configAttributes) {
        for (RoleResource roleResource : roleResourceRepository.findByResource(resource)) {
            configAttributes.add(new SecurityConfig(roleResource.getRole()));
            resourceList.put(new AntPathRequestMatcher(resource.getUrl(), resource.getMethod()), configAttributes);
        }
    }

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> loggingAuthorizationTable(final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceList) {
        log.info("#################################################################################");
        log.info("################################# Resource List #################################");
        log.info("#################################################################################");
        resourceList.forEach((key, value) -> log.info(key + " : " + value));
        log.info("#################################################################################");
        return resourceList;
    }

    @Transactional
    public RoleHierarchyImpl assembleAuthorityHierarchy() {
        final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(getRoleHierarchyStringRepresentation());
        return roleHierarchy;
    }

    private String getRoleHierarchyStringRepresentation() {
        return new StringBuilder()
            .append(
                roleHierarchiesRepository.findRoleHierarchiesByOrders(ORDERS_OF_SYS_ADMIN).stream()
                    .map(RoleHierarchies::getAuthority)
                    .collect(Collectors.joining(HIERARCHY_DELIMITER)))
            .append(HIERARCHY_JOIN_STR)
            .append(
                roleHierarchiesRepository.findRoleHierarchiesByOrders(ORDERS_OF_ADMIN).stream()
                    .map(RoleHierarchies::getAuthority)
                    .collect(Collectors.joining(HIERARCHY_DELIMITER)))
            .append(HIERARCHY_JOIN_STR)
            .append(
                roleHierarchiesRepository.findRoleHierarchiesByOrders(ORDERS_OF_USER).stream()
                    .map(RoleHierarchies::getAuthority)
                    .collect(Collectors.joining(HIERARCHY_DELIMITER)))
            .toString();
    }

}
