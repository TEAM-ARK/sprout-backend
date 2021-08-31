package com.ark.inflearnback.config.logger.http;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHttpLog is a Querydsl query type for HttpLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHttpLog extends EntityPathBase<HttpLog> {

    private static final long serialVersionUID = 1091178123L;

    public static final QHttpLog httpLog = new QHttpLog("httpLog");

    public final com.ark.inflearnback.domain.QAbstractEntity _super = new com.ark.inflearnback.domain.QAbstractEntity(this);

    public final StringPath clientIp = createString("clientIp");

    public final StringPath httpMethod = createString("httpMethod");

    public final NumberPath<Integer> httpStatusCode = createNumber("httpStatusCode", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath requestBody = createString("requestBody");

    public final StringPath requestUri = createString("requestUri");

    public final StringPath responseBody = createString("responseBody");

    public final StringPath token = createString("token");

    public QHttpLog(String variable) {
        super(HttpLog.class, forVariable(variable));
    }

    public QHttpLog(Path<? extends HttpLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHttpLog(PathMetadata metadata) {
        super(HttpLog.class, metadata);
    }

}

