package com.ark.inflearnback.domain.course;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.lecture.Lecture;
import com.ark.inflearnback.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public Course(User student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
