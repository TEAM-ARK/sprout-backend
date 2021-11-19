package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.ark.sprout.form.StudyPostForm;
import com.ark.sprout.type.RoleType;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class StudyPostTest {

    private static final Member FM1 = getMember("fakeMember1@test.com"); // fakeMember1
    private static final Member FM2 = getMember("fakeMember2@test.com"); // fakeMember2
    private static final StudyPostForm SPF1 = StudyPostForm.of("D2om1y", "s3I9AlN5FPjfuEGJzpw94nmL2EIyy64T2"); // studyPostForm1
    private static final StudyPostForm SPF2 = StudyPostForm.of("AGJAG125", "78Yh5ivdO07v1cREGL0132XpMGX53P8udv5f6r473"); // studyPostForm2

    private static Member getMember(final String email) {
        return Member.builder()
            .email(email)
            .password("1234")
            .socialId("")
            .registrationId("")
            .role(getRole())
            .isSocial(true)
            .build();
    }

    private static Role getRole() {
        return Role.builder()
            .roleType(RoleType.USER)
            .deleted(false)
            .build();
    }

    @Test
    void newPost() throws Exception {
        // given, when
        StudyPost studyPost = StudyPost.newPost(FM1, SPF1);

        // then
        assertThat(studyPost)
            .extracting("title", "content", "writer")
            .contains(SPF1.getTitle(), SPF1.getContent(), FM1);
    }

    @Test
    void edit() throws Exception {
        // given
        StudyPost studyPost = StudyPost.newPost(FM1, SPF1);

        // when
        List<EditHistory> editHistories = studyPost.edit(FM1, SPF2);

        // then
        assertThat(editHistories.size()).isEqualTo(1);

        assertThat(studyPost)
            .extracting("title", "content")
            .contains(SPF2.getTitle(), SPF2.getContent());

        assertThat(editHistories)
            .extracting("title", "content")
            .contains(Tuple.tuple(SPF1.getTitle(), SPF1.getContent()));
    }

    @Test
    void edit_exception() throws Exception {
        // given
        StudyPost studyPost = StudyPost.newPost(FM1, SPF1);

        // when, then
        assertThatThrownBy(() -> {
            studyPost.edit(FM2, SPF2);
        }).isInstanceOf(BadCredentialsException.class)
            .hasMessage("Only the owner can !");
    }

}
