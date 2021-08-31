package com.ark.inflearnback.config.jasypt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@Profile("prod")
@PropertySource(value = "file:${user.home}/team-ark-backend/jasypt-configuration.properties", ignoreResourceNotFound = true)
public class JasyptProperties {

    @Value("${jasypt.config.password}")
    private String password;
}
