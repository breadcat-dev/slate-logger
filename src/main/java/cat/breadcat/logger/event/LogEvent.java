package cat.breadcat.logger.event;


import cat.breadcat.logger.LogLevel;


public record LogEvent(
        LogContext context,
        LogException exception,
        LogThread thread,

        LogTimestamp timestamp,
        Class<?> clazz,
        LogLevel level,
        String message
)
{}