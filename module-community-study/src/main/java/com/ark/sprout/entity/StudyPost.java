package com.ark.sprout.entity;

import com.ark.sprout.form.StudyPostForm;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyPost {

    private Long id;

    private String title;

    private String content;

    private Member writer;

    private EditHistories editHistories;

    private boolean deleted;

    public static StudyPost newPost(@NotNull final Member writer, @NotNull final StudyPostForm postForm) {
        return StudyPost.builder()
            .title(postForm.getTitle())
            .content(postForm.getContent())
            .writer(writer)
            .editHistories(EditHistories.newHistories())
            .deleted(false)
            .build();
    }

    private boolean isOwner(@NotNull final Member loginUser) {
        return writer.equals(loginUser);
    }

    public List<EditHistory> edit(@NotNull final Member loginUser, @NotNull final StudyPostForm postForm) {
        if (isOwner(loginUser)) {
            List<EditHistory> editHistories = this.editHistories.edit(EditHistory.of(this.title, this.content));
            updatePost(postForm);
            return editHistories;
        }
        throw new BadCredentialsException("Only the owner can !");
    }

    private void updatePost(final StudyPostForm postForm) {
        this.title = postForm.getTitle();
        this.content = postForm.getContent();
    }

}
