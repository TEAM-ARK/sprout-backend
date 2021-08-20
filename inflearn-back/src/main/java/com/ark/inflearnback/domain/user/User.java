package com.ark.inflearnback.domain.user;

import com.ark.inflearnback.common.BaseTimeEntity;
import com.ark.inflearnback.domain.course.Course;
import com.ark.inflearnback.domain.enums.Role;
import com.ark.inflearnback.domain.lecture.Lecture;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private Boolean isSubscribed;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.GENERAL_USER; // 처음 회원가입할 때는 모두 일반회원으로 생성!


    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();
}
