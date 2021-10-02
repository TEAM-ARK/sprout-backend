package com.ark.inflearnback.domain.member.utils;

import java.util.Random;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Certification {

    private final String email;
    private final int certificateNumber;

    public static Certification of(String email) {
        final Random random = new Random();
        return new Certification(email, random.nextInt(899999) + 100000);
    }

    public boolean isNotEqualTo(String key) {
        return certificateNumber != Integer.parseInt(key);
    }

}
