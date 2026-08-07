package cat.breadcat.logger;


import cat.breadcat.logger.event.LogContext;
import cat.breadcat.logger.event.LogContextKeys;
import cat.breadcat.logger.event.LogException;


public final class LogBuilder
{
    // ===== Fields =====

    private final Logger logger;
    private final LogLevel level;
    private LogContext context;
    private LogException exception;

    // ===== Constructors =====

    LogBuilder(Logger logger, LogLevel level)
    {
        this.logger = logger;
        this.level = level;
        this.context = LogContext.empty();
        this.exception = null;
    }

    // ===== Configuration =====

    public LogBuilder with(String key, Object value)
    {
        this.context = this.context.with(key, value);

        return this;
    }

    public LogBuilder category(String value)
    {
        this.context = this.context.with(LogContextKeys.CATEGORY, value);

        return this;
    }

    public LogBuilder exception(Throwable throwable)
    {
        this.exception = LogException.of(throwable);

        return this;
    }

    // ===== Logging =====

    public void log(String message, Object... args)
    {
        logger.log(context, exception, level, message, args);
    }
}
