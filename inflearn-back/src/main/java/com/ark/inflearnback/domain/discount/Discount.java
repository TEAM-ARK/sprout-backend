package com.ark.inflearnback.domain.discount;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ark.inflearnback.domain.lecture.Lecture;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    //관계매핑
    private Lecture lecture_id;

    @NotNull
    private Integer discount_percent;

    @NotNull
    private LocalDateTime discount_period;
}
