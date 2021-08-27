package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.annotation.ExtensionJpaTest;
import com.ark.inflearnback.annotation.EnableContainers;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@ExtensionJpaTest
@EnableContainers
class MemberRepositoryTest {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    public MemberRepositoryTest(final MemberRepository memberRepository, final RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    void save() throws Exception {
        Role role = roleRepository.save(Role.of("ROLE_USER", "사용자", false));
        Member member = memberRepository.save(Member.of("siro@gmail.com", "password", role));
        Assertions.assertThat(member.getEmail()).isEqualTo("siro@gmail.com");
        Assertions.assertThat(member.getPassword()).isEqualTo("password");
        Assertions.assertThat(member.getRole()).isEqualTo(role);
    }
}