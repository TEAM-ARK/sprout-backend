package com.ark.sprout.repository;


import com.ark.sprout.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    boolean existsByEmail(final String email);

    Optional<Member> findBySocialId(final String socialId);

}
