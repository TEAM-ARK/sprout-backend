package com.ark.inflearnback.configuration.token;

import com.ark.inflearnback.domain.member.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class TokenScheduler {

    private final TokenGenerator tokenGenerator;

    @Scheduled(fixedDelay = 1000 * 60)
    public void cleanUp() {
        log.info("Clean up expired token");
        new Thread(tokenGenerator).start();
    }
}
