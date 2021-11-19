package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import com.ark.sprout.form.NewPostForm;
import com.ark.sprout.type.RoleType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudyPostTest {

    private Member fakeMember;
    private NewPostForm PF1;
    private NewPostForm PF2;

    @BeforeEach
    void setUp() {
        // given
        PF1 = NewPostForm.of("D2om1y", "s3I9AlN5FPjfuEGJzpw94nmL2EIyy64T2");
        PF2 = NewPostForm.of("AGJAG125", "78Yh5ivdO07v1cREGL0132XpMGX53P8udv5f6r473");
        fakeMember = Member.builder()
            .email("fakeMember@test.com")
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

    }

    @Test
    void newPost() throws Exception {
        // when
        StudyPost newPost = StudyPost.newPost(fakeMember, PF1);

        // then
        assertThat(newPost.getTitle()).isEqualTo(PF1.getTitle());
        assertThat(newPost.getContent()).isEqualTo(PF1.getContent());
        assertThat(newPost.getWriter()).isEqualTo(fakeMember);
    }

    @Test
    void edit() throws Exception {
        // given
        StudyPost studyPost = StudyPost.newPost(fakeMember, PF1);

        // when
        List<EditHistory> editHistories = studyPost.edit(fakeMember, PF2);

        // then
        assertThat(studyPost.getTitle()).isEqualTo(PF2.getTitle());
        assertThat(studyPost.getContent()).isEqualTo(PF2.getContent());

        EditHistory editHistory = editHistories.get(0);
        assertThat(editHistories.size()).isEqualTo(1);
        assertThat(editHistory.getTitle()).isEqualTo(PF1.getTitle());
        assertThat(editHistory.getContent()).isEqualTo(PF1.getContent());
    }

}
