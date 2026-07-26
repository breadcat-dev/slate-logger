package cat.breadcat.logger.formatter;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.ansi.Ansi;
import cat.breadcat.logger.ansi.AnsiColor;
import cat.breadcat.logger.event.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public final class ColorFormatter implements LogFormatter
{
    // CONSTRUCTOR
    private static final ColorFormatter INSTANCE = new ColorFormatter();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern(
                    "yyyy-MM-dd HH:mm:ss.SSS"
            )
            .withZone(
                    ZoneId.systemDefault()
            );

    private ColorFormatter() {}
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static ColorFormatter instance()
    {
        return INSTANCE;
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    @Override
    public String format(LogEvent event)
    {
        LogContext context = event.context();
        LogThread thread = event.thread();
        LogException exception = event.exception();

        Object category = context.get(
                LogContextKeys.CATEGORY
        );

        Instant timestamp = event.timestamp();
        String className = event.className();
        LogLevel level = event.level();
        String message = event.message();


        String formattedThread = "";
        if(thread != null)
        {
            String threadName = thread.name();

            formattedThread =
                    "-" +
                    ((threadName.isBlank()) ?
                    "thread" + thread.id() :
                    threadName);
        }

        String formattedException = "";
        if(exception != null)
        {
            formattedException =
                    "\n" +
                    exception.stackTrace();
        }

        String formattedCategory = "";
        if(category != null)
        {
            formattedCategory =
                    "-" +
                    category;
        }

        String formattedTimestamp = dateFormatter.format(timestamp);
        String formattedClassName = className + formattedThread;
        String formattedLevel = level.toString() + formattedCategory;


        String coloredException = Ansi.color(formattedException, level.color());

        String coloredTimestamp = Ansi.color(formattedTimestamp, AnsiColor.CYAN);
        String coloredClassName = Ansi.color(formattedClassName, AnsiColor.MAGENTA);
        String coloredLevel = Ansi.color(formattedLevel, level.color());


        return "(" + coloredTimestamp + ") [" + coloredClassName + "] <" + coloredLevel + "> " + message + coloredException;
    }
    // ~~PUBLIC~~
}
