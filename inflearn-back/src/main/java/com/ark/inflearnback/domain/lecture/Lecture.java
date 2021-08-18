package com.ark.inflearnback.domain.lecture;

import com.ark.inflearnback.domain.enums.Difficulty;
import com.ark.inflearnback.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ark.inflearnback.domain.discount.Discount;
import com.ark.inflearnback.domain.lectureHashtag.LectureHashtag;
import com.ark.inflearnback.domain.review.Review;
import com.ark.inflearnback.domain.enums.Role;
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
    @Column(name = "lecture_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User instructor;

    @OneToMany(mappedBy = "lecture",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();


    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LectureHashtag> hashtags = new ArrayList<>();

    @OneToOne(mappedBy = "lecture",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Discount discount;
}
