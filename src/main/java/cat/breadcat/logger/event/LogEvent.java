package cat.breadcat.logger.event;


import cat.breadcat.logger.LogLevel;


public record LogEvent(
        // DEFINITION
        LogContext context,
        LogException exception,
        LogThread thread,

        LogTimestamp timestamp,
        String className,
        LogLevel level,
        String message
        // ~~DEFINITION~~
)
{}