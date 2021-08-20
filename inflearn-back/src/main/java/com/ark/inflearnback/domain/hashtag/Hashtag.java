package com.ark.inflearnback.domain.hashtag;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Hashtag extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String value;
}
