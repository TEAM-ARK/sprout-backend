package com.ark.inflearnback.domain.security.model;

import com.ark.inflearnback.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resource extends AbstractEntity {
    private String url;
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
