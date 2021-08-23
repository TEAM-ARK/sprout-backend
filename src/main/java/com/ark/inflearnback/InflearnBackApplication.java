package com.ark.inflearnback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class InflearnBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(InflearnBackApplication.class, args);
    }
}
