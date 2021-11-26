package com.ark.sprout.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EditHistory implements Comparable<EditHistory> {

    private String title;

    private String content;

    private int version;

    public EditHistory(final String title, final String content, final int version) {
        this.title = title;
        this.content = content;
        this.version = version;
    }

    public static EditHistory of(final String title, final String content, final int version) {
        return new EditHistory(title, content, version);
    }

    @Override
    public int compareTo(final EditHistory o) {
        return this.version - o.version;
    }

}
