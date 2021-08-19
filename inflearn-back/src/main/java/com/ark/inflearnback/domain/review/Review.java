package com.ark.inflearnback.domain.review;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.lecture.Lecture;
import lombok.*;
import com.ark.inflearnback.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTimeEntity {

    /** TODO : 엔티티 클래스 생성
     * - User : Reivew = 1 : N (단방향 연관관계)
     * - REVIEW에서만 USER를 참조가능하다는 의미
     *
     */
    @Id @GeneratedValue
    @NotNull
    @Column(name = "review_id")
    private Long id;

    @NotEmpty
    private String content;

    @NotEmpty
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;
}
