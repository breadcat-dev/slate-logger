package cat.breadcat.logger.event;


import cat.breadcat.logger.LogLevel;

import java.time.Instant;


public record LogEvent(
        // DEFINITION
        LogContext context,
        LogException exception,
        LogThread thread,

        Instant timestamp,
        String className,
        LogLevel level,
        String message
        // ~~DEFINITION~~
)
{}