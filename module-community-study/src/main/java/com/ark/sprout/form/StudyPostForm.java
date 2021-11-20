package com.ark.sprout.form;

import lombok.Data;

@Data
public class StudyPostForm {

    private String title;

    private String content;

    private StudyPostForm(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public static StudyPostForm of(final String title, final String content) {
        return new StudyPostForm(title, content);
    }

}
