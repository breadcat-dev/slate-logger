package cat.breadcat.logger.event;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public final class LogTimestamp
{
    // CONSTRUCTOR
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter
            .ofPattern(
                    "yyyy-MM-dd HH:mm:ss.SSS"
            )
            .withZone(
                    ZoneId.systemDefault()
            );

    private final Instant instant;

    private LogTimestamp(
            Instant instant
    )
    {
        this.instant = instant;
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static LogTimestamp capture()
    {
        return new LogTimestamp(
                Instant.now()
        );
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    public String format()
    {
        return DEFAULT_FORMATTER.format(instant);
    }

    public String format(
            DateTimeFormatter formatter
    )
    {
        return Objects.requireNonNull(
                formatter, "formatter"
        ).format(instant);
    }
    // ~~PUBLIC~~
}
