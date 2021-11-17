package com.ark.sprout.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import java.util.Objects;

public abstract class LogUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String INFO_FORMAT = "--✅> [{0}] {1} {2}";
    private static final String WARN_FORMAT = "--⚠> [{0}] {1} {2}";
    private static final String ERROR_FORMAT = "--❌> [{0}] {1} {2}";
    private static final String CUSTOM_FORMAT = "--{0}> [{1}] {2} {3}";

    private static final int INVOKER = 2;

    public LogUtils() {}

    /**
     * Called when the log level is info and the parameter does not exist.
     *
     * @param message log message
     * @return formatted log message
     */
    public static String info(final String message) {
        Objects.requireNonNull(message);
        return MessageFormat.format(INFO_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * Called when the log level is info and the parameter does exist.
     *
     * @param message log message
     * @param param   log parameter
     * @return formatted log message
     */
    public static String info(final String message, final Object param) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(param);
        return MessageFormat.format(INFO_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * Called when the log level is warn and the parameter does not exist.
     *
     * @param message log message
     * @return formatted log message
     */
    public static String warn(final String message) {
        Objects.requireNonNull(message);
        return MessageFormat.format(WARN_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * Called when the log level is warn and the parameter does exist.
     *
     * @param message log message
     * @param param   log parameter
     * @return formatted log message
     */
    public static String warn(final String message, final Object param) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(param);
        return MessageFormat.format(WARN_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * Called when the log level is error and the parameter does not exist.
     *
     * @param message log message
     * @return formatted log message
     */
    public static String error(final String message) {
        Objects.requireNonNull(message);
        return MessageFormat.format(ERROR_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * Called when the log level is error and the parameter does exist.
     *
     * @param message log message
     * @param param   log parameter
     * @return formatted log message
     */
    public static String error(final String message, final Object param) {
        Objects.requireNonNull(message);
        Objects.requireNonNull(param);
        return MessageFormat.format(ERROR_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * Called when the log level is not info and error and the parameter does not exist.
     *
     * @param emoji   emoji to be displayed first
     * @param message log message
     * @return formatted log message
     */
    public static String custom(final String emoji, final String message) {
        Objects.requireNonNull(emoji);
        Objects.requireNonNull(message);
        return MessageFormat.format(CUSTOM_FORMAT, emoji, invokerMethodName(), message, "");
    }

    /**
     * Called when the log level is not info and error and the parameter does exist.
     *
     * @param emoji   emoji to be displayed first
     * @param message log message
     * @param param   log parameter
     * @return formatted log message
     */
    public static String custom(final String emoji, final String message, final Object param) {
        Objects.requireNonNull(emoji);
        Objects.requireNonNull(message);
        Objects.requireNonNull(param);
        return MessageFormat.format(CUSTOM_FORMAT, emoji, invokerMethodName(), message, toJsonString(param));
    }

    private static String invokerMethodName() {
        return new Throwable().getStackTrace()[INVOKER].getMethodName();
    }

    private static String toJsonString(final Object param) {
        try {
            return OBJECT_MAPPER.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            return param.toString();
        }
    }

}
