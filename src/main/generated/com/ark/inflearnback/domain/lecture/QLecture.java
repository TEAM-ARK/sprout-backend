package com.ark.inflearnback.domain.lecture;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLecture is a Querydsl query type for Lecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLecture extends EntityPathBase<Lecture> {

    private static final long serialVersionUID = 1542006471L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLecture lecture = new QLecture("lecture");

    public final com.ark.inflearnback.common.QBaseTimeEntity _super = new com.ark.inflearnback.common.QBaseTimeEntity(this);

    public final StringPath child_category = createString("child_category");

    public final StringPath cover_image = createString("cover_image");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final EnumPath<com.ark.inflearnback.domain.enums.Difficulty> difficulty = createEnum("difficulty", com.ark.inflearnback.domain.enums.Difficulty.class);

    public final com.ark.inflearnback.domain.discount.QDiscount discount;

    public final ListPath<com.ark.inflearnback.domain.lectureHashtag.LectureHashtag, com.ark.inflearnback.domain.lectureHashtag.QLectureHashtag> hashtags = this.<com.ark.inflearnback.domain.lectureHashtag.LectureHashtag, com.ark.inflearnback.domain.lectureHashtag.QLectureHashtag>createList("hashtags", com.ark.inflearnback.domain.lectureHashtag.LectureHashtag.class, com.ark.inflearnback.domain.lectureHashtag.QLectureHashtag.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ark.inflearnback.domain.user.QUser instructor;

    public final StringPath intro = createString("intro");

    public final BooleanPath isExclusive = createBoolean("isExclusive");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath parent_category = createString("parent_category");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<com.ark.inflearnback.domain.review.Review, com.ark.inflearnback.domain.review.QReview> reviews = this.<com.ark.inflearnback.domain.review.Review, com.ark.inflearnback.domain.review.QReview>createList("reviews", com.ark.inflearnback.domain.review.Review.class, com.ark.inflearnback.domain.review.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.ark.inflearnback.domain.video.Video, com.ark.inflearnback.domain.video.QVideo> videos = this.<com.ark.inflearnback.domain.video.Video, com.ark.inflearnback.domain.video.QVideo>createList("videos", com.ark.inflearnback.domain.video.Video.class, com.ark.inflearnback.domain.video.QVideo.class, PathInits.DIRECT2);

    public QLecture(String variable) {
        this(Lecture.class, forVariable(variable), INITS);
    }

    public QLecture(Path<? extends Lecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLecture(PathMetadata metadata, PathInits inits) {
        this(Lecture.class, metadata, inits);
    }

    public QLecture(Class<? extends Lecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.discount = inits.isInitialized("discount") ? new com.ark.inflearnback.domain.discount.QDiscount(forProperty("discount"), inits.get("discount")) : null;
        this.instructor = inits.isInitialized("instructor") ? new com.ark.inflearnback.domain.user.QUser(forProperty("instructor")) : null;
    }

}

