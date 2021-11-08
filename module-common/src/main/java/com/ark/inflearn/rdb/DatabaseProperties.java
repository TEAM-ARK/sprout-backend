package com.ark.inflearn.rdb;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@Profile("prod")
@PropertySource(
    value = "file:${user.home}/team-ark-backend/database-information.properties",
    ignoreResourceNotFound = true
)
public class DatabaseProperties {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

}
