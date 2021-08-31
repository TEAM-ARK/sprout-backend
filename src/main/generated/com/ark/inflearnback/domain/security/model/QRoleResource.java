package com.ark.inflearnback.domain.security.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleResource is a Querydsl query type for RoleResource
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoleResource extends EntityPathBase<RoleResource> {

    private static final long serialVersionUID = 1149081392L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleResource roleResource = new QRoleResource("roleResource");

    public final com.ark.inflearnback.domain.QAbstractEntity _super = new com.ark.inflearnback.domain.QAbstractEntity(this);

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final QResource resource;

    public final QRole role;

    public QRoleResource(String variable) {
        this(RoleResource.class, forVariable(variable), INITS);
    }

    public QRoleResource(Path<? extends RoleResource> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleResource(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleResource(PathMetadata metadata, PathInits inits) {
        this(RoleResource.class, metadata, inits);
    }

    public QRoleResource(Class<? extends RoleResource> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resource = inits.isInitialized("resource") ? new QResource(forProperty("resource")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

