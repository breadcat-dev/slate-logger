package cat.breadcat.logger;


import cat.breadcat.logger.event.*;
import cat.breadcat.logger.sink.LogSink;

import java.util.Objects;


public final class Logger
{
    // ===== Fields =====

    private final LoggingSystem system;
    private final LogSink[] sinks;
    private final LogLevel minimum;
    private final Class<?> clazz;
    private final boolean captureThread;

    // ===== Constructors =====

    Logger(LogSink[] sinks, LogLevel minimum, Class<?> clazz, boolean captureThread)
    {
        Objects.requireNonNull(sinks, "sinks");
        Objects.requireNonNull(minimum, "minimum");
        Objects.requireNonNull(clazz, "class");

        this.sinks = sinks.clone();
        this.minimum = minimum;
        this.clazz = clazz;
        this.captureThread = captureThread;
        this.system = LoggingSystem.instance();
        this.system.register(sinks);
    }

    // ===== Factories =====

    public static LoggerBuilder builder()
    {
        return new LoggerBuilder();
    }

    public LogBuilder atLevel(LogLevel level)
    {
        return new LogBuilder(this, level);
    }

    // ===== Logging =====

    void log(
            LogContext context, LogException exception, LogLevel level,
            String message, Object... args
    )
    {
        if(!level.isAtLeast(minimum))
            return;

        LogThread thread = captureThread ? LogThread.capture() : null;
        LogTimestamp timestamp = LogTimestamp.capture();
        String formattedMessage = format(message, args);
        LogEvent event = new LogEvent(
                context, exception, thread,
                timestamp, clazz, level,
                formattedMessage
        );

        system.submit(new LogTask(event, sinks));
    }

    // ===== Formatting =====

    private String format(String message, Object... args)
    {
        Objects.requireNonNull(message, "message");
        if(message.length() < 2 || args == null || args.length == 0)
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

    // ===== Simple =====

    public void debug(String message, Object... args)
    {
        log(LogContext.empty(), null, LogLevel.DEBUG, message, args);
    }

    public void info(String message, Object... args)
    {
        log(LogContext.empty(), null, LogLevel.INFO, message, args);
    }

    public void warn(String message, Object... args)
    {
        log(LogContext.empty(), null, LogLevel.WARN, message, args);
    }

    public void error(String message, Object... args)
    {
        log(LogContext.empty(), null, LogLevel.ERROR, message, args);
    }

    public void critical(String message, Object... args)
    {
        log(LogContext.empty(), null, LogLevel.CRITICAL, message, args);
    }

    // ===== Advanced =====

    public LogBuilder atDebug()
    {
        return atLevel(LogLevel.DEBUG);
    }

    public LogBuilder atInfo()
    {
        return atLevel(LogLevel.INFO);
    }

    public LogBuilder atWarn()
    {
        return atLevel(LogLevel.WARN);
    }

    public LogBuilder atError()
    {
        return atLevel(LogLevel.ERROR);
    }

    public LogBuilder atCritical()
    {
        return atLevel(LogLevel.CRITICAL);
    }
}
