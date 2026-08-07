package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;

import java.util.Objects;


public abstract class AbstractLogSink implements LogSink
{
    // ===== Fields =====

    private final LogFormatter formatter;

    // ===== Constructors =====

    public AbstractLogSink(LogFormatter formatter)
    {
        Objects.requireNonNull(formatter, "formatter");

        this.formatter = formatter;
    }

    // ===== Formatting =====

    protected String format(LogEvent event)
    {
        Objects.requireNonNull(event, "event");

        return formatter.format(event);
    }
}
