package com.ark.inflearnback.configuration.http.repository;

import com.ark.inflearnback.configuration.http.model.entity.HttpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogQueryRepository {

}
