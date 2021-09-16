package com.ark.inflearnback.configuration.database;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Autowired(required = false)
    private DatabaseProperties databaseProperties;

    @Bean
    @Primary
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create()
            .url(databaseProperties.getUrl())
            .username(databaseProperties.getUsername())
            .password(databaseProperties.getPassword())
            .build();
    }

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:h2:mem:test")
            .username("sa")
            .password("")
            .build();
    }

}
