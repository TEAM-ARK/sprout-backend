package com.ark.sprout.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EditHistories {

    private List<EditHistory> editHistories = new ArrayList<>();

    public static EditHistories newHistories() {
        return new EditHistories();
    }

    public List<EditHistory> edit(@NotNull final EditHistory editHistory) {
        editHistories.add(editHistory);
        return new ArrayList<>(editHistories);
    }

}
