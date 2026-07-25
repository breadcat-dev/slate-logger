package cat.breadcat.logger.formatter;


import cat.breadcat.logger.ansi.Ansi;
import cat.breadcat.logger.ansi.AnsiColor;
import cat.breadcat.logger.event.LogContext;
import cat.breadcat.logger.event.LogContextKeys;
import cat.breadcat.logger.event.LogEvent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public final class PlainFormatter implements LogFormatter
{
    private static final PlainFormatter INSTANCE = new PlainFormatter();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());

    private PlainFormatter() {}


    @Override
    public String format(LogEvent event)
    {
        LogContext context = event.context();

        String thread = "";
        if(context.has(LogContextKeys.THREAD_NAME) && context.has(LogContextKeys.THREAD_ID))
        {
            String threadName = context.get(LogContextKeys.THREAD_NAME).toString();
            String threadId = context.get(LogContextKeys.THREAD_ID).toString();
            thread = "-" + (threadName.isBlank() ? "thread" + threadId : threadName);
        }

        String category = "";
        if(context.has(LogContextKeys.CATEGORY))
            category = "-" + context.get(LogContextKeys.CATEGORY).toString();


        String timestamp = this.dateFormatter.format(event.timestamp());
        String className = event.className() + thread;
        String level = event.level() + category;
        String message = event.message();


        return "(" + timestamp + ") [" + className + "] <" + level + "> " + message;
    }


    public static PlainFormatter instance()
    {
        return INSTANCE;
    }
}
