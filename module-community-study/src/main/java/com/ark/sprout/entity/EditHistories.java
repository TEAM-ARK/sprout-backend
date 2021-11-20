package com.ark.sprout.entity;

import java.util.Set;
import java.util.TreeSet;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EditHistories {

    private Set<EditHistory> editHistories = new TreeSet<>();

    public static EditHistories newHistories() {
        return new EditHistories();
    }

    public Set<EditHistory> edit(@NotNull final EditHistory editHistory) {
        editHistories.add(editHistory);
        return new TreeSet<>(editHistories);
    }

    public Set<EditHistory> histories() {
        return new TreeSet<>(editHistories);
    }

    public int size() {
        return editHistories.size();
    }

    public int newVersion() {
        if (editHistories.size() == 0) {
            return 1;
        }
        return editHistories.stream()
            .max(EditHistory::compareTo)
            .get()
            .getVersion() + 1;
    }

}
