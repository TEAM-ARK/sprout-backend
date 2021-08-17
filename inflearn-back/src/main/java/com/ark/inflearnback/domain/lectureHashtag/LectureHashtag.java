package com.ark.inflearnback.domain.lectureHashtag;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ark.inflearnback.domain.hashtag.Hashtag;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureHashtag {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    //관계매핑?
    private List<Lecture> lectures = new ArrayList<>();

    //관계매핑
    private Hashtag hashtag;
}