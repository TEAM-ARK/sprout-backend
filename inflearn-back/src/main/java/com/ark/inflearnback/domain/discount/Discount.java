package com.ark.inflearnback.domain.discount;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Discount extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @NotNull
    private Integer discountPercent;

    @NotNull
    private LocalDateTime discountPeriod;
}
