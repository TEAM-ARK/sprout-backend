package team.ark.sprout.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
