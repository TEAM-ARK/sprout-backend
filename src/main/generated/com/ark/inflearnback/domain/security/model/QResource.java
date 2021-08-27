package com.ark.inflearnback.domain.security.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QResource is a Querydsl query type for Resource
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResource extends EntityPathBase<Resource> {

    private static final long serialVersionUID = -2144162150L;

    public static final QResource resource = new QResource("resource");

    public final com.ark.inflearnback.domain.QAbstractEntity _super = new com.ark.inflearnback.domain.QAbstractEntity(this);

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath method = createString("method");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath url = createString("url");

    public QResource(String variable) {
        super(Resource.class, forVariable(variable));
    }

    public QResource(Path<? extends Resource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResource(PathMetadata metadata) {
        super(Resource.class, metadata);
    }

}

