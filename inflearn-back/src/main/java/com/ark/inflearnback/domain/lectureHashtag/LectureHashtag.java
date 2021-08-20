package com.ark.inflearnback.domain.lectureHashtag;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;
import com.ark.inflearnback.domain.hashtag.Hashtag;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureHashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    public LectureHashtag(Lecture lecture, Hashtag hashtag) {
        this.lecture = lecture;
        this.hashtag = hashtag;
    }
}