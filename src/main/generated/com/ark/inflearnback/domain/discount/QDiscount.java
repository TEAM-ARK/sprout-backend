package com.ark.inflearnback.domain.discount;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiscount is a Querydsl query type for Discount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDiscount extends EntityPathBase<Discount> {

    private static final long serialVersionUID = -1544069369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiscount discount = new QDiscount("discount");

    public final com.ark.inflearnback.common.QBaseTimeEntity _super = new com.ark.inflearnback.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> discountPercent = createNumber("discountPercent", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> discountPeriod = createDateTime("discountPeriod", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.ark.inflearnback.domain.lecture.QLecture lecture;

    public QDiscount(String variable) {
        this(Discount.class, forVariable(variable), INITS);
    }

    public QDiscount(Path<? extends Discount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiscount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiscount(PathMetadata metadata, PathInits inits) {
        this(Discount.class, metadata, inits);
    }

    public QDiscount(Class<? extends Discount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new com.ark.inflearnback.domain.lecture.QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

