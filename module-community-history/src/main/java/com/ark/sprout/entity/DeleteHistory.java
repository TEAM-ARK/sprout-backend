package com.ark.sprout.entity;

import com.ark.sprout.type.ContentType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteHistory {

    private ContentType contentType;

    private long contentId;

    private Member deleteBy;

    private LocalDateTime deleteAt;

    public static DeleteHistory ofQuestion(final ContentType contentType, final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(ContentType.QUESTION, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofFree(final ContentType contentType, final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(ContentType.FREE, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofStudy(final ContentType contentType, final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(ContentType.STUDY, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofComment(final ContentType contentType, final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(ContentType.COMMENT, contentId, deleteBy, deleteAt);
    }

}
