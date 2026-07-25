package cat.breadcat.logger.event;


import cat.breadcat.logger.LogLevel;

import java.time.Instant;


public record LogEvent(
        LogContext context,
        Instant timestamp,
        String className,
        LogLevel level,
        String message
)
{

}