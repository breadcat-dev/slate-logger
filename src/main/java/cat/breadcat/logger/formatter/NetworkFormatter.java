package cat.breadcat.logger.formatter;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.ansi.Ansi;
import cat.breadcat.logger.ansi.AnsiColor;
import cat.breadcat.logger.event.*;

import java.util.Objects;


public final class NetworkFormatter implements LogFormatter
{
    // ===== Constants =====

    private static final NetworkFormatter INSTANCE = new NetworkFormatter();

    // ===== Constructors =====

    private NetworkFormatter()
    {
    }

    // ===== Factories =====

    public static NetworkFormatter instance()
    {
        return INSTANCE;
    }

    // ===== Formatting =====

    @Override
    public String format(LogEvent event)
    {
        Objects.requireNonNull(event.context(), "context");
        Objects.requireNonNull(event.timestamp(), "timestamp");
        Objects.requireNonNull(event.clazz(), "clazz");
        Objects.requireNonNull(event.level(), "level");
        Objects.requireNonNull(event.message(), "message");

        LogContext context = event.context();
        LogThread thread = event.thread();
        LogException exception = event.exception();
        Object category = context.get(LogContextKeys.CATEGORY);
        Object user = context.get("user");
        Object address = context.get("address");
        LogTimestamp timestamp = event.timestamp();
        String className = event.clazz().getSimpleName();
        LogLevel level = event.level();
        String message = event.message();

        String formattedThread =
                (thread != null) ?
                        "-" + (thread.name().isBlank() ? "thread" + thread.id() : thread.name()) :
                        "";
        String formattedException =
                (exception != null) ?
                        "\n" + exception.stackTrace() :
                        "";
        String formattedCategory =
                (category != null) ?
                        "-" + category :
                        "";
        String formattedUser = user + "-" + address;
        String formattedTimestamp = timestamp.format();
        String formattedClassName = className + formattedThread;
        String formattedLevel = level + formattedCategory;

        String coloredException = Ansi.color(formattedException, level.color());
        String coloredUser = Ansi.color(formattedUser, AnsiColor.YELLOW);
        String coloredTimestamp = Ansi.color(formattedTimestamp, AnsiColor.CYAN);
        String coloredClassName = Ansi.color(formattedClassName, AnsiColor.MAGENTA);
        String coloredLevel = Ansi.color(formattedLevel, level.color());

        return "(" + coloredTimestamp + ") [" + coloredClassName + "] {" + coloredUser + "} <" + coloredLevel + "> " + message + coloredException;
    }
}