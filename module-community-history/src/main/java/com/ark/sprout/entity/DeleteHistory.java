package com.ark.sprout.entity;

import com.ark.sprout.type.ContentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private ContentType contentType;

    private long contentId;

    @ManyToOne
    private Member deleteBy;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime deleteAt;

    public static DeleteHistory ofQuestion(final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(null, ContentType.QUESTION, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofFree(final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(null, ContentType.FREE, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofStudy(final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(null, ContentType.STUDY, contentId, deleteBy, deleteAt);
    }

    public static DeleteHistory ofComment(final long contentId, final Member deleteBy, final LocalDateTime deleteAt) {
        return new DeleteHistory(null, ContentType.COMMENT, contentId, deleteBy, deleteAt);
    }

}
