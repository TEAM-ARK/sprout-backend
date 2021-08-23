package com.ark.inflearnback.domain.video;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.lecture.Lecture;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public Video(String url, Lecture lecture) {
        this.url = url;
        this.lecture = lecture;
    }
}
