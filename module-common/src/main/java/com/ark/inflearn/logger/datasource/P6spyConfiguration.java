package com.ark.inflearn.logger.datasource;

import com.p6spy.engine.spy.P6SpyOptions;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfiguration {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance()
            .setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
    }

}
