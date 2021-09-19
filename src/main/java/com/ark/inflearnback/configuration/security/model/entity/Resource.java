package com.ark.inflearnback.configuration.security.model.entity;

import com.ark.inflearnback.domain.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resource extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String url;

    @Column(nullable = false)
    private String method;

    private boolean deleted;

    private Resource(final String url, final String method, final boolean deleted) {
        this.url = url;
        this.method = method;
        this.deleted = deleted;
    }

    @Builder
    public static Resource of(final String url, final String method, final boolean deleted) {
        return new Resource(url, method, deleted);
    }

}
