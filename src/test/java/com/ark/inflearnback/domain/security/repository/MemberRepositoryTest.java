package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.annotation.EnableContainers;
import com.ark.inflearnback.annotation.ExtensionJpaTest;

@ExtensionJpaTest
@EnableContainers
class MemberRepositoryTest {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    public MemberRepositoryTest(final MemberRepository memberRepository, final RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }
}