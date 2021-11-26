package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import com.ark.sprout.type.ContentType;
import com.ark.sprout.type.RoleType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DeleteHistoryTest {

    private Member deletedBy;
    private LocalDateTime deleteAt;

    @BeforeEach
    void setUp() {
        deletedBy = Member.builder()
            .email("email")
            .password("password")
            .socialId("socialId")
            .registrationId("registrationId")
            .role(Role.builder()
                .roleType(RoleType.USER)
                .deleted(false)
                .build())
            .isSocial(true)
            .build();

        deleteAt = LocalDate.of(2000, 12, 12).atStartOfDay();
    }

    @ParameterizedTest
    @EnumSource(ContentType.class)
    void ofQuestion(final ContentType contentType) {
        assertThat(deleteHistoryOf(contentType))
            .extracting("contentType", "contentId", "deleteBy", "deleteAt")
            .contains(contentType, 1L, deletedBy, deleteAt);
    }

    private DeleteHistory deleteHistoryOf(final ContentType contentType) {
        if (ContentType.QUESTION == contentType) {
            return DeleteHistory.ofQuestion(1L, deletedBy, deleteAt);
        }
        if (ContentType.STUDY == contentType) {
            return DeleteHistory.ofStudy(1L, deletedBy, deleteAt);
        }
        if (ContentType.FREE == contentType) {
            return DeleteHistory.ofFree(1L, deletedBy, deleteAt);
        }
        return DeleteHistory.ofComment(1L, deletedBy, deleteAt);
    }

}
