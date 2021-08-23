package com.ark.inflearnback.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -85611321L;

    public static final QUser user = new QUser("user");

    public final com.ark.inflearnback.common.QBaseTimeEntity _super = new com.ark.inflearnback.common.QBaseTimeEntity(this);

    public final ListPath<com.ark.inflearnback.domain.course.Course, com.ark.inflearnback.domain.course.QCourse> courses = this.<com.ark.inflearnback.domain.course.Course, com.ark.inflearnback.domain.course.QCourse>createList("courses", com.ark.inflearnback.domain.course.Course.class, com.ark.inflearnback.domain.course.QCourse.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSubscribed = createBoolean("isSubscribed");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<com.ark.inflearnback.domain.lecture.Lecture, com.ark.inflearnback.domain.lecture.QLecture> lectures = this.<com.ark.inflearnback.domain.lecture.Lecture, com.ark.inflearnback.domain.lecture.QLecture>createList("lectures", com.ark.inflearnback.domain.lecture.Lecture.class, com.ark.inflearnback.domain.lecture.QLecture.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<com.ark.inflearnback.domain.enums.Role> role = createEnum("role", com.ark.inflearnback.domain.enums.Role.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

