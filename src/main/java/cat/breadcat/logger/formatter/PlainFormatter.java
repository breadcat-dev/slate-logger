package cat.breadcat.logger.formatter;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.event.*;

import java.util.Objects;


public final class PlainFormatter implements LogFormatter
{
    // IN - INTERNAL
    // OUT - EXTERNAL

    // CONSTRUCTOR
    private static final PlainFormatter INSTANCE = new PlainFormatter();


    private PlainFormatter() {}
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static PlainFormatter instance()
    {
        return INSTANCE;
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    @Override
    public String format(LogEvent event)
    {
        LogContext context = Objects.requireNonNull(
                event.context(), "context"
        );
        LogThread thread = event.thread();
        LogException exception = event.exception();

        Object category = context.get(
                LogContextKeys.CATEGORY
        );

        LogTimestamp timestamp = Objects.requireNonNull(
                event.timestamp(), "timestamp"
        );
        String className = Objects.requireNonNull(
                event.className(), "className"
        );
        LogLevel level = Objects.requireNonNull(
                event.level(), "level"
        );
        String message = Objects.requireNonNull(
                event.message(), "message"
        );



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


        String formattedTimestamp = timestamp.format();
        String formattedClassName = className + formattedThread;
        String formattedLevel = level.toString() + formattedCategory;


        return "(" + formattedTimestamp + ") [" + formattedClassName + "] <" + formattedLevel + "> " + message + formattedException;
    }
    // ~~PUBLIC~~
}
