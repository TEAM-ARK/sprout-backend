package com.ark.inflearnback.domain.lecture;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.enums.Difficulty;
import com.ark.inflearnback.domain.user.User;
import lombok.*;
import com.ark.inflearnback.domain.discount.Discount;
import com.ark.inflearnback.domain.lectureHashtag.LectureHashtag;
import com.ark.inflearnback.domain.review.Review;
import com.ark.inflearnback.domain.video.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Lecture extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    private String name;
    private String intro;
    private Integer price;
    private boolean isExclusive;
    private String cover_image;
    //카드형 강의설명
    private String description;
    private String parent_category;
    private String child_category;

    //강의 난이도: 초~중고급
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User instructor;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();


    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LectureHashtag> hashtags = new ArrayList<>();

    @OneToOne(mappedBy = "lecture",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Discount discount;
}
