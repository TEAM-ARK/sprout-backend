package com.ark.inflearnback.domain.lectureHashtag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureHashtag is a Querydsl query type for LectureHashtag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLectureHashtag extends EntityPathBase<LectureHashtag> {

    private static final long serialVersionUID = 497667111L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureHashtag lectureHashtag = new QLectureHashtag("lectureHashtag");

    public final com.ark.inflearnback.common.QBaseTimeEntity _super = new com.ark.inflearnback.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final com.ark.inflearnback.domain.hashtag.QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.ark.inflearnback.domain.lecture.QLecture lecture;

    public QLectureHashtag(String variable) {
        this(LectureHashtag.class, forVariable(variable), INITS);
    }

    public QLectureHashtag(Path<? extends LectureHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureHashtag(PathMetadata metadata, PathInits inits) {
        this(LectureHashtag.class, metadata, inits);
    }

    public QLectureHashtag(Class<? extends LectureHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new com.ark.inflearnback.domain.hashtag.QHashtag(forProperty("hashtag")) : null;
        this.lecture = inits.isInitialized("lecture") ? new com.ark.inflearnback.domain.lecture.QLecture(forProperty("lecture"), inits.get("lecture")) : null;
    }

}

