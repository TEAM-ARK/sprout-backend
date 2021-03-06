package team.ark.sprout.common.util;

import static java.util.Arrays.stream;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Objects;
import org.slf4j.Logger;

public abstract class LogUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        OBJECT_MAPPER.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static final String INFO_FORMAT = "--๐ข> [{}] {} {}";
    private static final String WARN_FORMAT = "--๐ก> [{}] {} {}";
    private static final String ERROR_FORMAT = "--๐ > [{}] {} {}";
    private static final String CUSTOM_FORMAT = "--{}> [{}] {} {}";

    private static final int INVOKER_INDEX = 2;

    public LogUtils() {
    }

    /**
     * ๋ก๊ทธ ๋ ๋ฒจ์ด info ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ์ง ์์ ๋ ํธ์ถํ์ญ์์ค.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void info(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.info(INFO_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * ๋ก๊ทธ ๋ ๋ฒจ์ด info ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ  ๋ ํธ์ถํ์ญ์์ค.
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
     * ๋ก๊ทธ ๋ ๋ฒจ์ด warn ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ์ง ์์ ๋ ํธ์ถํ์ญ์์ค.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void warn(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.warn(WARN_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * ๋ก๊ทธ ๋ ๋ฒจ์ด warn ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ  ๋ ํธ์ถํ์ญ์์ค.
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
     * ๋ก๊ทธ ๋ ๋ฒจ์ด error ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ์ง ์์ ๋ ํธ์ถํ์ญ์์ค.
     *
     * @param logger  org.slf4j.Logger
     * @param message log message
     */
    public static void error(final Logger logger, final String message) {
        requiredNonNulls(logger, message);
        logger.error(ERROR_FORMAT, invokerMethodName(), message, "");
    }

    /**
     * ๋ก๊ทธ ๋ ๋ฒจ์ด error ์ด๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ  ๋ ํธ์ถํ์ญ์์ค.
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
     * ๋ก๊ทธ ๋ ๋ฒจ์ด info ๋ฐ error ๊ฐ ์๋๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ์ง ์์ ๋ ํธ์ถํ์ญ์์ค.
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
     * ๋ก๊ทธ ๋ ๋ฒจ์ด info ๋ฐ error ๊ฐ ์๋๊ณ  ๋งค๊ฐ๋ณ์๊ฐ ์กด์ฌํ  ๋ ํธ์ถํ์ญ์์ค.
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
