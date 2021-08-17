package com.ark.inflearnback.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ark.inflearnback.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    /** TODO : 엔티티 클래스 생성
     * - User : Reivew = 1 : N (단방향 연관관계)
     * - REVIEW에서만 USER를 참조가능하다는 의미
     *
     */
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotEmpty
    private String content;

    @NotEmpty
    private Long rating;

    //관계 매핑
    private User student;
}
