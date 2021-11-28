package com.ark.sprout.entity;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditHistory extends AbstractEntity implements Comparable<EditHistory> {

    private String title;

    private String content;

    private int version;

    public static EditHistory of(final String title, final String content, final int version) {
        return new EditHistory(title, content, version);
    }

    @Override
    public int compareTo(final EditHistory o) {
        return this.version - o.version;
    }

}
