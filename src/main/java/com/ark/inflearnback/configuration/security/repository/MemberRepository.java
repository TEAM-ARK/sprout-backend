package com.ark.inflearnback.configuration.security.repository;

import com.ark.inflearnback.configuration.security.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    boolean existsByEmail(final String email);

    Optional<Member> findBySocialId(final String socialId);
}
