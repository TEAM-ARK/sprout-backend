package team.ark.sprout.common.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import team.ark.sprout.common.util.LogUtils;

@DisplayName("LogUtils 테스트")
class LogUtilsTest {
    Logger logger;
    ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        listAppender = new ListAppender<>();
        logger = (Logger) LoggerFactory.getLogger(this.getClass());
        logger.addAppender(listAppender);
        listAppender.start();
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    @DisplayName("INFO - 메시지만 있는 경우")
    void info_1() {
        // ...when
        LogUtils.info(logger, "infoMessage");

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--✅> [info_1] infoMessage ");
    }

    @Test
    @DisplayName("INFO - 메시지와 파라미터가 있는 경우")
    void info_2() {
        // ...when
        LogUtils.info(logger, "infoMessage", createDummy());

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--✅> [info_2] infoMessage {\"id\":1,\"name\":\"dummy class\"}");
    }

    @Test
    @DisplayName("WARN - 메시지만 있는 경우")
    void warn_1() {
        // ...when
        LogUtils.warn(logger, "warnMessage");

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--⚠️> [warn_1] warnMessage ");
    }

    @Test
    @DisplayName("WARN - 메시지와 파라미터가 있는 경우")
    void warn_2() {
        // ...when
        LogUtils.warn(logger, "warnMessage", createDummy());

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--⚠️> [warn_2] warnMessage {\"id\":1,\"name\":\"dummy class\"}");
    }

    @Test
    @DisplayName("ERROR - 메시지만 있는 경우")
    void error_1() {
        // ...when
        LogUtils.error(logger, "errorMessage");

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--❌> [error_1] errorMessage ");
    }

    @Test
    @DisplayName("ERROR - 메시지와 파라미터가 있는 경우")
    void error_2() {
        // ...when
        LogUtils.error(logger, "errorMessage", createDummy());

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--❌> [error_2] errorMessage {\"id\":1,\"name\":\"dummy class\"}");
    }

    @Test
    @DisplayName("CUSTOM - 이모지와 메시지만 있는 경우")
    void custom_1() {
        // ...when
        LogUtils.custom(logger, "❤", "customMessage");

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--❤> [custom_1] customMessage ");
    }

    @Test
    @DisplayName("CUSTOM - 이모지와 메시지, 파라미터가 있는 경우")
    void custom_2() {
        // ...when
        LogUtils.custom(logger, "❤", "customMessage", createDummy());

        // ...then
        List<ILoggingEvent> list = listAppender.list;
        assertThat(list.get(0).getFormattedMessage()).isEqualTo("--❤> [custom_2] customMessage {\"id\":1,\"name\":\"dummy class\"}");
    }

    @Test
    @DisplayName("NPE가 발생하는 경우")
    void nullPointerException() throws Exception {
        assertAll(
            () -> assertThatThrownBy(() -> {
                LogUtils.info(null, "");
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.info(logger, null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.info(logger, "", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.warn(null, "");
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.warn(logger, null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.warn(logger, "", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.error(null, "");
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.error(logger, null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.error(logger, "", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom(null, "", "");
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom(logger, null, "", createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom(logger, "❤", null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom(logger, "❤", "", null);
            }).isInstanceOf(NullPointerException.class)
        );
    }

    private Dummy createDummy() {
        return Dummy.of(1L, "dummy class");
    }

    private static class Dummy {
        private final Long id;
        private final String name;

        private Dummy(final Long id, final String name) {
            this.id = id;
            this.name = name;
        }

        public static Dummy of(final Long id, final String name) {
            return new Dummy(id, name);
        }

        @Override
        public String toString() {
            return "Dummy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
        }
    }
}
