package com.ark.inflearnback.domain.lecture;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ark.inflearnback.domain.discount.Discount;
import com.ark.inflearnback.domain.lectureHashtag.LectureHashtag;
import com.ark.inflearnback.domain.review.Review;
import com.ark.inflearnback.domain.user.Role;
import com.ark.inflearnback.domain.user.User;
import com.ark.inflearnback.domain.video.Video;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private String intro;

    @NotNull
    private Integer price;

    @NotNull
    private boolean isExclusive;


    private String cover_image;

    //카드형 강의설명
    private String description;

    //강의 난이도: 초~중고급
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @NotNull
    private String parent_category;

    private String child_category;

    private List<Role> students = new ArrayList<Role>();

    @NotNull
    private Role instructor = Role.SHARER;

    //관계매핑
    private List<Review> reviews = new ArrayList<>();;

    //관계매핑
    private List<Video> videos = new ArrayList<>();;

    //관계매핑
    private List<LectureHashtag> hashtags = new ArrayList<>();;

    //관계매핑
    private Discount discount;
}
