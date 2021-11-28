package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EditHistoriesTest {

    private EditHistories editHistories;

    @BeforeEach
    void setUp() {
        editHistories = EditHistories.newHistories();
    }

    @Test
    @DisplayName("컨텐츠 수정 이력을 추가한다")
    void add() {
        EditHistory editHistory = getEditHistory(1);
        assertThat(editHistories.add(editHistory)).contains(editHistory);
    }

    @Test
    @DisplayName("컨텐츠 수정 이력을 추가할 경우 nextVersion()이 반환하는 값은 가장 마지막에 추가된 이력의 버전보다 1만큼 더 크다")
    void nextVersion() {
        int version;
        int expectedVersion;
        for (expectedVersion = 1; expectedVersion < 100; expectedVersion++) {
            version = editHistories.nextVersion();
            assertThat(version).isEqualTo(expectedVersion);
            editHistories.add(getEditHistory(version));
        }
    }

    private EditHistory getEditHistory(final int version) {
        return EditHistory.of("title", "content", version);
    }

}
