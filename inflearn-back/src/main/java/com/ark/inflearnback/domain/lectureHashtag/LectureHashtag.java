package com.ark.inflearnback.domain.lectureHashtag;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;
import com.ark.inflearnback.domain.hashtag.Hashtag;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LectureHashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}