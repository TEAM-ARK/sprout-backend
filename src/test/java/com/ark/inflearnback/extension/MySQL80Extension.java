package com.ark.inflearnback.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

public class MySQL80Extension implements BeforeAllCallback, AfterAllCallback {
    private MySQLContainer<?> MYSQL;

    private static final String DATABASE_NAME = "inflearn_backend";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int PORT = 3306;

    @Override
    public void beforeAll(final ExtensionContext context) {
        MYSQL = new MySQLContainer<>("mysql:8.0.26")
                .withDatabaseName(DATABASE_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(PORT);

        MYSQL.start();

        String jdbcUrl = String.format("jdbc:mysql://localhost:%d/%s", MYSQL.getFirstMappedPort(), DATABASE_NAME);
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", USERNAME);
        System.setProperty("spring.datasource.password", PASSWORD);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        MYSQL.stop();
    }
}
