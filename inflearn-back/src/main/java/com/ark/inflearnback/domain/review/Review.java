package com.ark.inflearnback.domain.review;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;
import com.ark.inflearnback.domain.user.User;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String content;
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;
}
