package com.ark.inflearnback.domain.lecture;

import com.ark.inflearnback.domain.enums.Difficulty;
import com.ark.inflearnback.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LectureTest {

    @Test
    @DisplayName("빌더 패턴을 사용하여 새로운 강의 생성")
    void lectureByBuilderTest() {

        // 실제로 강의를 새로만들 때는 로그인한 지식 공유자의 정보를 통해 instructor를 넣어야함.
        // 회원에게 보여지는 강의에 대한 정보는 가공되어 보여줘야함.(DTO)
        User instructor = new User("민철", "smc5236@naver.com", "1234", true);

        Lecture newLecture = Lecture.createLectureByBuilder()
                .name("스프링 정복기")
                .intro("asdfasdf")
                .price(50000)
                .isExclusive(true)
                .cover_image("asdfasdf")
                .description("스프링을 정복하는 강의")
                .parent_category("개발/프로그래밍")
                .child_category("웹 개발")
                .difficulty(Difficulty.ADVANCED)
                .instructor(instructor)
                .build();

        assertThat(newLecture.getName()).isEqualTo("스프링 정복기");


    }
}