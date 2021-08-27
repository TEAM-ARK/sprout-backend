package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.annotation.MySQLContainers;
import com.ark.inflearnback.annotation.QuerydslTest;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuerydslTest
@MySQLContainers
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
        assertThat(member.getEmail()).isEqualTo("siro@gmail.com");
        assertThat(member.getPassword()).isEqualTo("password");
        assertThat(member.getRole()).isEqualTo(role);
    }

    @Test
    void findByEmail() {
        Role role = roleRepository.save(Role.of("ROLE_USER", "사용자", false));
        memberRepository.save(Member.of("siro@gmail.com", "password", role));

        Member result = memberRepository.findByEmail("siro@gmail.com").orElseThrow(IllegalArgumentException::new);
        result.update("lsj@gmail.com");
        memberRepository.save(result);
        assertThat(result.getEmail()).isEqualTo("lsj@gmail.com");
    }
}