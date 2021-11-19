package com.ark.sprout.form;

import lombok.Data;

@Data
public class NewPostForm {

    private String title;

    private String content;

    private NewPostForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static NewPostForm of(String title, String content) {
        return new NewPostForm(title, content);
    }

}
