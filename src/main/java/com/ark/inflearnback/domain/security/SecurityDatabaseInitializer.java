package com.ark.inflearnback.domain.security;

import com.ark.inflearnback.domain.security.filter.UrlFilterInvocationSecurityMetadataSource;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.Resource;
import com.ark.inflearnback.domain.security.model.Role;
import com.ark.inflearnback.domain.security.model.RoleHierarchies;
import com.ark.inflearnback.domain.security.model.RoleResource;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.repository.ResourceRepository;
import com.ark.inflearnback.domain.security.repository.RoleHierarchiesRepository;
import com.ark.inflearnback.domain.security.repository.RoleRepository;
import com.ark.inflearnback.domain.security.repository.RoleResourceRepository;
import com.ark.inflearnback.domain.security.service.SecurityResourceService;
import java.util.HashMap;
import java.util.Map;
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
            roleRepository.save(roles.get("member"));

            final Member sysadmin = createMember("sys", encryptedPassword, roles.get("sys"));
            final Member admin = createMember("admin", encryptedPassword, roles.get("admin"));
            final Member member = createMember("member", encryptedPassword, roles.get("member"));

            memberRepository.save(sysadmin);
            memberRepository.save(admin);
            memberRepository.save(member);

            final Resource resourceHome = createResource("/", "GET");
            final Resource resourceMember = createResource("/member", "GET");
            final Resource resourceAdmin = createResource("/admin", "GET");
            final Resource resourceSysadmin = createResource("/sys", "GET");

            resourceRepository.save(resourceHome);
            resourceRepository.save(resourceMember);
            resourceRepository.save(resourceAdmin);
            resourceRepository.save(resourceSysadmin);

            roleResourceRepository.save(createRoleResource(roles.get("sys"), resourceSysadmin));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), resourceAdmin));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), resourceMember));
            roleResourceRepository.save(createRoleResource(roles.get("sys"), resourceHome));

            roleResourceRepository.save(createRoleResource(roles.get("admin"), resourceAdmin));
            roleResourceRepository.save(createRoleResource(roles.get("admin"), resourceMember));
            roleResourceRepository.save(createRoleResource(roles.get("admin"), resourceHome));

            roleResourceRepository.save(createRoleResource(roles.get("member"), resourceMember));
            roleResourceRepository.save(createRoleResource(roles.get("member"), resourceHome));

            createHierarchy();
            securityResourceService.assembleAuthorityHierarchy();
            metadataSource.reload();

        }

        private void createHierarchy() {
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_SYS_ADMIN", 1));
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_ADMIN", 2));
            roleHierarchiesRepository.save(createRoleHierarchies("ROLE_MEMBER", 3));
        }

        private RoleHierarchies createRoleHierarchies(final String authority, final int orders) {
            return RoleHierarchies.builder()
                    .authority(authority)
                    .orders(orders)
                    .build();
        }

        private Map<String, Role> createRoles() {
            return new HashMap<>() {{
                put("sys", createRole("ROLE_SYS_ADMIN", "시스템관리자"));
                put("admin", createRole("ROLE_ADMIN", "관리자"));
                put("member", createRole("ROLE_MEMBER", "사용자"));
            }};
        }

        private Role createRole(final String role, final String description) {
            return Role.builder()
                    .authority(role)
                    .description(description)
                    .deleted(false)
                    .build();
        }

        private Member createMember(final String email, final String password, final Role role) {
            return Member.builder()
                    .email(email)
                    .password(password)
                    .role(role)
                    .build();
        }

        private RoleResource createRoleResource(final Role role, final Resource resource) {
            return RoleResource.builder()
                    .role(role)
                    .resource(resource)
                    .deleted(false)
                    .build();
        }

        private Resource createResource(final String url, final String method) {
            return Resource.builder()
                    .url(url)
                    .method(method)
                    .deleted(false)
                    .build();
        }
    }
}
