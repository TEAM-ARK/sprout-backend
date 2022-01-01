package team.ark.sprout.util;

import static java.util.Arrays.stream;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Objects;
import org.slf4j.Logger;

public abstract class LogUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    private static final String INFO_FORMAT = "--✅> [{}] {} {}";
    private static final String WARN_FORMAT = "--⚠️> [{}] {} {}";
    private static final String ERROR_FORMAT = "--❌> [{}] {} {}";
    private static final String CUSTOM_FORMAT = "--{}> [{}] {} {}";

    private static final int INVOKER_INDEX = 2;

    public LogUtils() {
    }

    /**
     * 로그 레벨이 info 이고 매개변수가 존재하지 않을 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void info(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.info(INFO_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * 로그 레벨이 info 이고 매개변수가 존재할 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     * @param param   log parameter
     */
    public static void info(final Logger logger, final String message, final Object param) {
        requiredNonNulls(logger, message, param);
        logger.info(INFO_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * 로그 레벨이 warn 이고 매개변수가 존재하지 않을 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void warn(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.warn(WARN_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * 로그 레벨이 warn 이고 매개변수가 존재할 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     * @param param   log parameter
     */
    public static void warn(final Logger logger, final String message, final Object param) {
        requiredNonNulls(logger, message, param);
        logger.warn(WARN_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * 로그 레벨이 error 이고 매개변수가 존재하지 않을 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void error(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.error(ERROR_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * 로그 레벨이 error 이고 매개변수가 존재할 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     * @param param   log parameter
     */
    public static void error(final Logger logger, final String message, final Object param) {
        requiredNonNulls(logger, message, param);
        logger.error(ERROR_FORMAT, invokerMethodName(), message, toJsonString(param));
    }

    /**
     * 로그 레벨이 info 및 error 가 아니고 매개변수가 존재하지 않을 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param emoji   emoji to be displayed first
     * @param message log message
     */
    public static void custom(final Logger logger, final String emoji, final String message) {
        requiredNonNulls(logger, emoji, message);
        logger.info(CUSTOM_FORMAT, emoji, invokerMethodName(), message, "");
    }

    /**
     * 로그 레벨이 info 및 error 가 아니고 매개변수가 존재할 때 호출하십시오.
     *
     * @param logger  org.slf4j.Logger
     * @param emoji   emoji to be displayed first
     * @param message log message
     * @param param   log parameter
     */
    public static void custom(final Logger logger, final String emoji, final String message, final Object param) {
        requiredNonNulls(logger, emoji, message, param);
        logger.info(CUSTOM_FORMAT, emoji, invokerMethodName(), message, toJsonString(param));
    }

    private static void requiredNonNulls(final Object... objects) {
        stream(objects).forEach(Objects::requireNonNull);
    }

    private static String invokerMethodName() {
        return new Throwable().getStackTrace()[INVOKER_INDEX].getMethodName();
    }

    private static String toJsonString(final Object param) {
        try {
            return OBJECT_MAPPER.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            return param.toString();
        }
    }
}
