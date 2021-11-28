package com.ark.sprout.entity;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditHistories {

    private Set<EditHistory> editHistories = new TreeSet<>();

    public static EditHistories newHistories() {
        return new EditHistories();
    }

    public Set<EditHistory> add(@NotNull final EditHistory editHistory) {
        editHistories.add(editHistory);
        return new TreeSet<>(editHistories);
    }

    public final int nextVersion() {
        if (editHistories.size() < 1) {
            return 1;
        }
        return editHistories.stream()
            .max(EditHistory::compareTo)
            .orElseThrow(NoSuchElementException::new)
            .getVersion() + 1;
    }

}
