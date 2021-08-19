package com.ark.inflearnback.domain.video;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.lecture.Lecture;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotBlank
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
