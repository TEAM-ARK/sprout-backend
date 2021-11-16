package com.ark.sprout.repository;

import static com.ark.sprout.entity.QHttpLog.httpLog;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class HttpLogQueryRepositoryImpl implements HttpLogQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Long searchDau() {
        return queryFactory
            .select(httpLog.clientIp.countDistinct())
            .from(httpLog)
            .where(httpLog.regDate.gt(onTime()))
            .fetchOne();
    }

    @Override
    @Transactional(readOnly = true)
    public Long searchTotalVisitors() {
        return queryFactory
            .select(httpLog.clientIp.countDistinct())
            .from(httpLog)
            .groupBy(date(httpLog.regDate))
            .fetch()
            .stream()
            .reduce(0L, Long::sum);
    }

    private StringTemplate date(final DateTimePath<LocalDateTime> regDate) {
        return stringTemplate("date({0})", regDate);
    }

    private LocalDateTime onTime() {
        return LocalDateTime.of(
            LocalDateTime.now().getYear(),
            LocalDateTime.now().getMonth(),
            LocalDateTime.now().getDayOfMonth(),
            0,
            0,
            0);
    }

}
