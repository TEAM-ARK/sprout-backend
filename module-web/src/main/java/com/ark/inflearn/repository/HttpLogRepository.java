package com.ark.inflearn.repository;

import com.ark.inflearn.entity.HttpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HttpLogRepository extends JpaRepository<HttpLog, Long>, HttpLogQueryRepository {

}
