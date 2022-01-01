package team.ark.sprout.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class DatabaseConfig {
    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(databaseProperties.getUrl())
            .username(databaseProperties.getUsername())
            .password(databaseProperties.getPassword())
            .build();
    }
}
