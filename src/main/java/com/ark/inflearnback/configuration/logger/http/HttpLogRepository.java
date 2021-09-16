package com.ark.inflearnback.configuration.logger.http;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogQueryRepository {

}
