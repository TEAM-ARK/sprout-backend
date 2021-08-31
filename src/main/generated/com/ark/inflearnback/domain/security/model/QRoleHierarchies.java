package com.ark.inflearnback.domain.security.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoleHierarchies is a Querydsl query type for RoleHierarchies
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoleHierarchies extends EntityPathBase<RoleHierarchies> {

    private static final long serialVersionUID = 1296001841L;

    public static final QRoleHierarchies roleHierarchies = new QRoleHierarchies("roleHierarchies");

    public final com.ark.inflearnback.domain.QAbstractEntity _super = new com.ark.inflearnback.domain.QAbstractEntity(this);

    public final StringPath authority = createString("authority");

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final NumberPath<Integer> orders = createNumber("orders", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QRoleHierarchies(String variable) {
        super(RoleHierarchies.class, forVariable(variable));
    }

    public QRoleHierarchies(Path<? extends RoleHierarchies> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleHierarchies(PathMetadata metadata) {
        super(RoleHierarchies.class, metadata);
    }

}

