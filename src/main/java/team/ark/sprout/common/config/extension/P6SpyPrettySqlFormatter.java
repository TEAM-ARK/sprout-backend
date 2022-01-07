package team.ark.sprout.common.config.extension;

import static java.util.Arrays.stream;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Stack;
import java.util.function.Predicate;
import org.hibernate.engine.jdbc.internal.FormatStyle;

public class P6SpyPrettySqlFormatter implements MessageFormattingStrategy {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String P6SPY_FORMATTER = "P6spyPrettySqlFormatter";
    private static final String PACKAGE = "com.ark";
    private static final String CREATE = "create";
    private static final String ALTER = "alter";
    private static final String COMMENT = "comment";

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return sqlFormatToUpper(sql, category, getMessage(connectionId, elapsed, getStackBuilder()));
    }

    private String sqlFormatToUpper(String sql, String category, String message) {
        if (sql.trim().isEmpty()) {
            return "";
        }
        return NEW_LINE
            + sqlFormatToUpper(sql, category)
            + message;
    }

    private String sqlFormatToUpper(String sql, String category) {
        if (isStatementDDL(sql, category)) {
            return FormatStyle.DDL
                .getFormatter()
                .format(sql)
                .toUpperCase(Locale.ROOT)
                .replace("+0900", "");
        }
        return FormatStyle.BASIC
            .getFormatter()
            .format(sql)
            .toUpperCase(Locale.ROOT)
            .replace("+0900", "");
    }

    private boolean isStatementDDL(String sql, String category) {
        return isStatement(category) && isDDL(sql.trim().toLowerCase(Locale.ROOT));
    }

    private boolean isStatement(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private boolean isDDL(String lowerSql) {
        return lowerSql.startsWith(CREATE)
            || lowerSql.startsWith(ALTER)
            || lowerSql.startsWith(COMMENT);
    }

    private String getMessage(int connectionId, long elapsed, StringBuilder callStackBuilder) {
        return NEW_LINE
            + NEW_LINE
            + "\t"
            + String.format("Connection ID: %s", connectionId)
            + NEW_LINE
            + "\t"
            + String.format("Execution Time: %s ms", elapsed)
            + NEW_LINE
            + NEW_LINE
            + "\t"
            + String.format("Call Stack (number 1 is entry point): %s", callStackBuilder)
            + NEW_LINE
            + NEW_LINE
            + "----------------------------------------------------------------------------------------------------";
    }

    private StringBuilder getStackBuilder() {
        Stack<String> callStack = new Stack<>();
        stream(new Throwable().getStackTrace())
            .map(StackTraceElement::toString)
            .filter(isExcludeWords())
            .forEach(callStack::push);

        int order = 1;
        StringBuilder callStackBuilder = new StringBuilder();
        while (!callStack.empty()) {
            callStackBuilder.append(MessageFormat.format("{0}\t\t{1}. {2}", NEW_LINE, order++, callStack.pop()));
        }
        return callStackBuilder;
    }

    private Predicate<String> isExcludeWords() {
        return charSequence -> charSequence.startsWith(PACKAGE) && !charSequence.contains(P6SPY_FORMATTER);
    }
}
