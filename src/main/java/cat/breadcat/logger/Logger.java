package cat.breadcat.logger;


import cat.breadcat.logger.event.*;
import cat.breadcat.logger.sink.LogSink;

import java.util.Objects;


public final class Logger
{
    // CONSTRUCTOR
    private final LoggingSystem system;

    private final LogSink[] sinks;
    private final LogLevel minimum;
    private final String className;
    private final boolean captureThread;

    Logger(
            LogSink[] sinks, LogLevel minimum,
            String className, boolean captureThread
    )
    {
        this.system = LoggingSystem.instance();

        this.sinks = Objects.requireNonNull(
                sinks, "sinks"
        );
        this.minimum = Objects.requireNonNull(
                minimum, "minimum"
        );
        this.className = Objects.requireNonNull(
                className, "className"
        );
        this.captureThread = captureThread;
    }
    // ~~CONSTRUCTOR~~

    // PRIVATE
    private String format(
            String message, Object... args
    )
    {
        if(message.length() < 2 || args.length == 0)
            return message;


        StringBuilder stringBuilder = new StringBuilder(message.length() * 2 / 3);
        int argument = 0;

        for(int i = 0; i < message.length(); i++)
        {
            if(
                    i + 1 < message.length() &&
                    message.charAt(i) == '{' &&
                    message.charAt(i + 1) == '}' &&
                    argument < args.length
            )
            {
                stringBuilder.append(args[argument++]);
                i++;
            }
            else
                stringBuilder.append(message.charAt(i));
        }


        return stringBuilder.toString();
    }
    // ~~PRIVATE~~

    // PACKAGE-PRIVATE
    void log(
            LogContext context, LogException exception,
            LogLevel level, String message,
            Object... args
    )
    {
        if(!level.isAtLeast(minimum))
            return;


        LogThread thread = captureThread ? LogThread.capture() : null;
        LogTimestamp timestamp = LogTimestamp.capture();

        String formattedMessage = format(
                Objects.requireNonNull(
                        message, "message"
                ),
                args
        );

        LogEvent event = new LogEvent(
                context, exception, thread,
                timestamp, className, level,
            formattedMessage
        );


        system.submit(new LogTask(
                event,
                sinks
        ));
    }
    // ~~PACKAGE-PRIVATE~~

    // PUBLIC STATIC
    public static LoggerBuilder builder()
    {
        return new LoggerBuilder();
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    public void debug(
            String message, Object... args
    )
    {
        log(
                LogContext.empty(),
                null,
                LogLevel.DEBUG,
                message,
                args
        );
    }

    public void info(
            String message, Object... args
    )
    {
        log(
                LogContext.empty(),
                null,
                LogLevel.INFO,
                message,
                args
        );
    }

    public void warn(
            String message, Object... args
    )
    {
        log(
                LogContext.empty(),
                null,
                LogLevel.WARN,
                message,
                args
        );
    }

    public void error(
            String message, Object... args
    )
    {
        log(
                LogContext.empty(),
                null,
                LogLevel.ERROR,
                message,
                args
        );
    }

    public void critical(
            String message, Object... args
    )
    {
        log(
                LogContext.empty(),
                null,
                LogLevel.CRITICAL,
                message,
                args
        );
    }


    public LogBuilder atLevel(
            LogLevel level
    )
    {
        return new LogBuilder(
                this,
                level
        );
    }

    public LogBuilder atDebug()
    {
        return atLevel(
                LogLevel.DEBUG
        );
    }

    public LogBuilder atInfo()
    {
        return atLevel(
                LogLevel.INFO
        );
    }

    public LogBuilder atWarn()
    {
        return atLevel(
                LogLevel.WARN
        );
    }

    public LogBuilder atError()
    {
        return atLevel(
                LogLevel.ERROR
        );
    }

    public LogBuilder atCritical()
    {
        return atLevel(
                LogLevel.CRITICAL
        );
    }
    // ~~PUBLIC~~
}
