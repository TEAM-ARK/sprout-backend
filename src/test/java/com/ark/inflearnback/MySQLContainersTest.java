package com.ark.inflearnback;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class MySQLContainersTest {
    private static final MySQLContainer MYSQL = new MySQLContainer("mysql:8.0.26").withDatabaseName("inflearn_backend");

    @BeforeAll
    static void beforeAll() {
        MYSQL.start();
    }

    @AfterAll
    static void afterAll() {
        MYSQL.stop();
    }

    @Test
    void isRunning() throws Exception {
        Assertions.assertThat(MYSQL.isRunning()).isTrue();
    }
}