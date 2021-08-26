package com.ark.inflearnback.domain.security;

import com.ark.inflearnback.domain.security.filter.UrlFilterInvocationSecurityMetadataSource;
import com.ark.inflearnback.domain.security.model.*;
import com.ark.inflearnback.domain.security.repository.*;
import com.ark.inflearnback.domain.security.service.SecurityResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class SecurityDatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final InitService initService;
    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(@NonNull final ContextRefreshedEvent event) {
        log.info("onApplicationEvent start");

        if (alreadySetup) {
            return;
        }

        log.info("Application setup...");

        initService.setUp();
        alreadySetup = true;
    }

    @Service
    @RequiredArgsConstructor
    private static class InitService {
        private final RoleRepository roleRepository;
        private final MemberRepository memberRepository;
        private final ResourceRepository resourceRepository;
        private final RoleResourceRepository roleResourceRepository;
        private final SecurityResourceService securityResourceService;
        private final RoleHierarchiesRepository roleHierarchiesRepository;
        private final UrlFilterInvocationSecurityMetadataSource metadataSource;

        @Transactional
        public void setUp() {
            final Map<String, Role> roles = createRoles();
            final String encryptedPassword = PasswordEncoderFactories
                    .createDelegatingPasswordEncoder()
                    .encode("root");

            roleRepository.save(roles.get("sys"));
            roleRepository.save(roles.get("admin"));
            roleRepository.save(roles.get("user"));

            final Member aSys = createAdmin("sys", encryptedPassword, roles.get("sys"));
            final Member aMember = createAdmin("admin", encryptedPassword, roles.get("admin"));
            final Member aUser = createAdmin("user", encryptedPassword, roles.get("user"));

            memberRepository.save(aSys);
            memberRepository.save(aMember);
            memberRepository.save(aUser);

            final Resource rHome = createResource("/", "GET");
            final Resource rUser = createResource("/user", "GET");
            final Resource rAdmin = createResource("/admin", "GET");
            final Resource rSys = createResource("/sys", "GET");

            resourceRepository.save(rHome);
            resourceRepository.save(rUser);
            resourceRepository.save(rAdmin);
            resourceRepository.save(rSys);

            roleResourceRepository.save(createRoleResource(roles.get("sys"), rSys));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), rAdmin));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), rUser));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), rHome));

            roleResourceRepository.save(createRoleResource(roles.get("admin"), rAdmin));
            roleResourceRepository.save(createRoleResource(roles.get("admin"), rUser));
            roleResourceRepository.save(createRoleResource(roles.get("admin"), rHome));

            roleResourceRepository.save(createRoleResource(roles.get("user"), rUser));
            roleResourceRepository.save(createRoleResource(roles.get("user"), rHome));

            createHierarchy();
            securityResourceService.assembleAuthorityHierarchy();
            metadataSource.reload();

        }

        private void createHierarchy() {
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_SYS_ADMIN", 1));
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_ADMIN", 2));
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_USER", 3));
        }

        private RoleHierarchies createRoleHierarchies(final String role_sys_admin, final int i) {
            return RoleHierarchies.builder()
                    .authority(role_sys_admin)
                    .orders(i)
                    .build();
        }

        private Map<String, Role> createRoles() {
            final Map<String, Role> roles = new HashMap<>();
            roles.put("sys", createRole("ROLE_SYS_ADMIN", "시스템관리자"));
            roles.put("admin", createRole("ROLE_ADMIN", "관리자"));
            roles.put("user", createRole("ROLE_USER", "유저"));
            return roles;

        }

        private Role createRole(final String role, final String description) {
            return Role.builder()
                    .authority(role)
                    .description(description)
                    .deleted(false)
                    .build();
        }

        private Member createAdmin(String username, String password, Role role) {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .role(role)
                    .build();
        }

        private RoleResource createRoleResource(Role role, Resource resource) {
            return RoleResource.builder()
                    .role(role)
                    .resource(resource)
                    .deleted(false)
                    .build();
        }

        private Resource createResource(String url, String method) {
            return Resource.builder()
                    .url(url)
                    .method(method)
                    .deleted(false)
                    .build();
        }
    }
}
