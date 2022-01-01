package team.ark.sprout.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan("team.ark.sprout.adapter.out.persistence")
@EnableJpaRepositories("team.ark.sprout.adapter.out.persistence")
public class JpaConfig {
}
