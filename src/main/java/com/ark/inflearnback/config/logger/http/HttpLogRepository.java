package com.ark.inflearnback.config.logger.http;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogCustomRepository {}
