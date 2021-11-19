package com.ark.sprout.form;

import lombok.Data;

@Data
public class StudyPostForm {

    private String title;

    private String content;

    private StudyPostForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static StudyPostForm of(String title, String content) {
        return new StudyPostForm(title, content);
    }

}
