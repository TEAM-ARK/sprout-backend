package com.ark.inflearnback.domain.security.repository;

import com.ark.inflearnback.domain.security.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    boolean existsByEmail(final String email);

    Optional<Member> findBySocialId(String socialId);
}
