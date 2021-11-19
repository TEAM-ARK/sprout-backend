package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.ark.sprout.form.NewPostForm;
import com.ark.sprout.type.RoleType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class StudyPostTest {

    private Member FM1; // fakeMember1
    private Member FM2; // fakeMember2
    private NewPostForm PF1; // postForm1
    private NewPostForm PF2; // postForm2

    @BeforeEach
    void setUp() {
        // given
        PF1 = NewPostForm.of("D2om1y", "s3I9AlN5FPjfuEGJzpw94nmL2EIyy64T2");

        PF2 = NewPostForm.of("AGJAG125", "78Yh5ivdO07v1cREGL0132XpMGX53P8udv5f6r473");

        FM1 = Member.builder()
            .email("fakeMember1@test.com")
            .password("1234")
            .socialId("sUcaYN8j02udIqOuU")
            .registrationId("k76jI0Xake11M4mLgF")
            .role(
                Role.builder()
                    .roleType(RoleType.USER)
                    .deleted(false)
                    .build()
            )
            .isSocial(true)
            .build();

        FM2 = Member.builder()
            .email("fakeMember2@test.com")
            .password("1234")
            .socialId("mCfNfCoRjqlog")
            .registrationId("sB1I76FgY5s82xzNWL")
            .role(
                Role.builder()
                    .roleType(RoleType.USER)
                    .deleted(false)
                    .build()
            )
            .isSocial(true)
            .build();

    }

    @Test
    void newPost() throws Exception {
        // when
        StudyPost newPost = StudyPost.newPost(FM1, PF1);

        // then
        assertThat(newPost.getTitle()).isEqualTo(PF1.getTitle());
        assertThat(newPost.getContent()).isEqualTo(PF1.getContent());
        assertThat(newPost.getWriter()).isEqualTo(FM1);
    }

    @Test
    void edit() throws Exception {
        // given
        StudyPost studyPost = StudyPost.newPost(FM1, PF1);

        // when
        List<EditHistory> editHistories = studyPost.edit(FM1, PF2);

        // then
        assertThat(studyPost.getTitle()).isEqualTo(PF2.getTitle());
        assertThat(studyPost.getContent()).isEqualTo(PF2.getContent());

        EditHistory editHistory = editHistories.get(0);
        assertThat(editHistories.size()).isEqualTo(1);
        assertThat(editHistory.getTitle()).isEqualTo(PF1.getTitle());
        assertThat(editHistory.getContent()).isEqualTo(PF1.getContent());
    }

    @Test
    void edit_exception() throws Exception {
        // given
        StudyPost studyPost = StudyPost.newPost(FM1, PF1);

        // when, then
        assertThatThrownBy(() -> {
            studyPost.edit(FM2, PF2);
        }).isInstanceOf(BadCredentialsException.class)
            .hasMessage("Only the owner can !");
    }

}
