package team.ark.sprout.common.config;

import com.p6spy.engine.spy.P6SpyOptions;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import team.ark.sprout.common.config.extension.P6SpyPrettySqlFormatter;

@Configuration
public class P6SpyConfig {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyPrettySqlFormatter.class.getName());
    }
}
