package com.ark.sprout.entity;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditHistoriesTest {

    private EditHistories editHistories;

    @BeforeEach
    void setUp() {
        editHistories = EditHistories.newHistories();
    }

    @Test
    void edit() throws Exception {
        // given
        int newVersion = editHistories.generateNewVersion();
        EditHistory editHistory = EditHistory.of("title", "content", newVersion);

        // when
        Set<EditHistory> histories = editHistories.edit(editHistory);

        // then
        assertThat(histories.size()).isEqualTo(1);
        assertThat(histories)
            .extracting("title", "content", "version")
            .contains(
                Tuple.tuple(editHistory.getTitle(), editHistory.getContent(), editHistory.getVersion())
            );
    }

    @Test
    void newVersion() {
        // given
        editHistories.edit(EditHistory.of("title", "content", 3));
        editHistories.edit(EditHistory.of("title", "content", 2));
        editHistories.edit(EditHistory.of("title", "content", 4));
        editHistories.edit(EditHistory.of("title", "content", 5));
        editHistories.edit(EditHistory.of("title", "content", 1));

        // when
        int version = editHistories.generateNewVersion();

        // then
        assertThat(version).isEqualTo(6);
    }

}
