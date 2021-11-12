package com.ark.inflearn.config;

import com.ark.inflearn.entity.Member;
import com.ark.inflearn.entity.Resource;
import com.ark.inflearn.entity.Role;
import com.ark.inflearn.entity.RoleHierarchies;
import com.ark.inflearn.entity.RoleResource;
import com.ark.inflearn.filter.UrlFilterInvocationSecurityMetadataSource;
import com.ark.inflearn.repository.MemberRepository;
import com.ark.inflearn.repository.ResourceRepository;
import com.ark.inflearn.repository.RoleHierarchiesRepository;
import com.ark.inflearn.repository.RoleRepository;
import com.ark.inflearn.repository.RoleResourceRepository;
import com.ark.inflearn.service.SecurityResourceService;
import com.ark.inflearn.type.RoleType;
import java.util.EnumMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
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
        private final FilterInvocationSecurityMetadataSource metadataSource;

        @Transactional
        public void setUp() {
            final Map<RoleType, Role> roles = createRoles();
            final String encryptedPassword =
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("root");

            roleRepository.save(roles.get(RoleType.SYS_ADMIN));
            roleRepository.save(roles.get(RoleType.ADMIN));
            roleRepository.save(roles.get(RoleType.USER));

            final Member sysadmin =
                createMember("sys@test.com", encryptedPassword, roles.get(RoleType.SYS_ADMIN));
            final Member admin =
                createMember("admin@test.com", encryptedPassword, roles.get(RoleType.ADMIN));
            final Member member = createMember("member@test.com", encryptedPassword, roles.get(RoleType.USER));

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

            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.SYS_ADMIN), resourceSysadmin));
            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.SYS_ADMIN), resourceAdmin));
            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.SYS_ADMIN), resourceMember));
            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.SYS_ADMIN), resourceHome));

            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.ADMIN), resourceAdmin));
            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.ADMIN), resourceMember));
            roleResourceRepository.save(
                createRoleResource(roles.get(RoleType.ADMIN), resourceHome));

            roleResourceRepository.save(createRoleResource(roles.get(RoleType.USER), resourceMember));
            roleResourceRepository.save(createRoleResource(roles.get(RoleType.USER), resourceHome));

            createHierarchy();
            securityResourceService.assembleAuthorityHierarchy();
            ((UrlFilterInvocationSecurityMetadataSource) this.metadataSource).reload();
        }

        private void createHierarchy() {
            roleHierarchiesRepository.save(createRoleHierarchies(RoleType.SYS_ADMIN, 1));
            roleHierarchiesRepository.save(createRoleHierarchies(RoleType.ADMIN, 2));
            roleHierarchiesRepository.save(createRoleHierarchies(RoleType.USER, 3));
        }

        private RoleHierarchies createRoleHierarchies(final RoleType roleType, final int orders) {
            return RoleHierarchies.builder().authority(roleType.get()).orders(orders).build();
        }

        private Map<RoleType, Role> createRoles() {
            return new EnumMap<>(
                Map.of(
                    RoleType.SYS_ADMIN, createRole(RoleType.SYS_ADMIN),
                    RoleType.ADMIN, createRole(RoleType.ADMIN),
                    RoleType.USER, createRole(RoleType.USER))
            );
        }

        private Role createRole(final RoleType roleType) {
            return Role.builder().roleType(roleType).deleted(false).build();
        }

        private Member createMember(final String email, final String password, final Role role) {
            return Member.builder().email(email).password(password).role(role).build();
        }

        private RoleResource createRoleResource(final Role role, final Resource resource) {
            return RoleResource.builder().role(role).resource(resource).deleted(false).build();
        }

        private Resource createResource(final String url, final String method) {
            return Resource.builder().url(url).method(method).deleted(false).build();
        }

    }

}
