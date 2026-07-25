package cat.breadcat.logger.formatter;


import cat.breadcat.logger.ansi.Ansi;
import cat.breadcat.logger.ansi.AnsiColor;
import cat.breadcat.logger.event.Log;
import cat.breadcat.logger.event.LogContext;
import cat.breadcat.logger.event.LogContextKeys;
import cat.breadcat.logger.event.LogEvent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public final class ColorFormatter implements LogFormatter
{
    private static final ColorFormatter INSTANCE = new ColorFormatter();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());

    private ColorFormatter() {}


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

        String coloredTimestamp = Ansi.color(timestamp, AnsiColor.CYAN);
        String coloredClassName = Ansi.color(className, AnsiColor.MAGENTA);
        String coloredLevel = Ansi.color(level, event.level().color());
        String message = event.message();


        return "(" + coloredTimestamp + ") [" + coloredClassName + "] <" + coloredLevel + "> " + message;
    }


    public static ColorFormatter instance()
    {
        return INSTANCE;
    }
}
