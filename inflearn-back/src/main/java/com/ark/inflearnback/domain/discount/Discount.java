package com.ark.inflearnback.domain.discount;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Discount extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Integer discountPercent;
    private LocalDateTime discountPeriod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

}
