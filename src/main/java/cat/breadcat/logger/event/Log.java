package cat.breadcat.logger.event;


import cat.breadcat.logger.LogLevel;


public final class Log
{
    private final LogLevel level;
    private final String message;
    private final LogContext context;

    private Log(LogLevel level, String message, LogContext context)
    {
        this.level = level;
        this.message = message;
        this.context = context;
    }


    public static Log debug(String message)
    {
        return new Log(
                LogLevel.DEBUG,
                message,
                LogContext.empty()
        );
    }

    public static Log info(String message)
    {
        return new Log(
                LogLevel.INFO,
                message,
                LogContext.empty()
        );
    }

    public static Log warn(String message)
    {
        return new Log(
                LogLevel.WARN,
                message,
                LogContext.empty()
        );
    }

    public static Log error(String message)
    {
        return new Log(
                LogLevel.ERROR,
                message,
                LogContext.empty()
        );
    }

    public static Log critical(String message)
    {
        return new Log(
                LogLevel.CRITICAL,
                message,
                LogContext.empty()
        );
    }


    public Log with(String key, Object value)
    {
        return new Log(
                this.level,
                this.message,
                this.context.with(key, value)
        );
    }

    public Log threadName(String value)
    {
        return with(LogContextKeys.THREAD_NAME, value);
    }

    public Log threadId(long value)
    {
        return with(LogContextKeys.THREAD_ID, value);
    }

    public Log category(String value)
    {
        return with(LogContextKeys.CATEGORY, value);
    }


    public LogLevel level()
    {
        return this.level;
    }

    public String message()
    {
        return this.message;
    }

    public LogContext context()
    {
        return this.context;
    }
}
