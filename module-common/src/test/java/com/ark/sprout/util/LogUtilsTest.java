package com.ark.sprout.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;

class LogUtilsTest {

    @Test
    void info_1() {
        String infoMessage = LogUtils.info("infoMessage", createDummy());
        assertThat(infoMessage).isEqualTo("--✅> [info_1] infoMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void info_2() {
        String infoMessage = LogUtils.info("infoMessage", createDummy());
        assertThat(infoMessage).isEqualTo("--✅> [info_2] infoMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void warn_1() {
        String infoMessage = LogUtils.warn("warnMessage", createDummy());
        assertThat(infoMessage).isEqualTo("--⚠> [warn_1] warnMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void warn_2() {
        String infoMessage = LogUtils.warn("warnMessage", createDummy());
        assertThat(infoMessage).isEqualTo("--⚠> [warn_2] warnMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void error_1() {
        String errorMessage = LogUtils.error("errorMessage", createDummy());
        assertThat(errorMessage).isEqualTo("--❌> [error_1] errorMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void error_2() {
        String errorMessage = LogUtils.error("errorMessage", createDummy());
        assertThat(errorMessage).isEqualTo("--❌> [error_2] errorMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void custom_1() {
        String customMessage = LogUtils.custom("❤", "customMessage", createDummy());
        assertThat(customMessage).isEqualTo("--❤> [custom_1] customMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void custom_2() {
        String customMessage = LogUtils.custom("❤", "customMessage", createDummy());
        assertThat(customMessage).isEqualTo("--❤> [custom_2] customMessage Dummy{id=1, name='dummy class'}");
    }

    @Test
    void nullPointerException() throws Exception {
        assertAll(
            () -> assertThatThrownBy(() -> {
                LogUtils.info(null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.info("infoMessage", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.warn(null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.warn("warnMessage", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.error(null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.error("errorMessage", null);
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom(null, "customMessage", createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom("❤", null, createDummy());
            }).isInstanceOf(NullPointerException.class),

            () -> assertThatThrownBy(() -> {
                LogUtils.custom("❤", "customMessage", null);
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
