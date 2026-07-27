package cat.breadcat.logger.formatter;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.event.*;

public final class PlainFormatter implements LogFormatter
{
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
        LogContext context = event.context();
        LogThread thread = event.thread();
        LogException exception = event.exception();

        Object category = context.get(
                LogContextKeys.CATEGORY
        );

        LogTimestamp timestamp = event.timestamp();
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


        String formattedTimestamp = timestamp.format();
        String formattedClassName = className + formattedThread;
        String formattedLevel = level.toString() + formattedCategory;


        return "(" + formattedTimestamp + ") [" + formattedClassName + "] <" + formattedLevel + "> " + message + formattedException;
    }
    // ~~PUBLIC~~
}
