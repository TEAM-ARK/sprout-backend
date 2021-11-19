package com.ark.sprout.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EditHistory {

    private String title;

    private String content;

    private EditHistory(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static EditHistory of(String title, String content) {
        return new EditHistory(title, content);
    }

}
