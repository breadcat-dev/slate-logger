package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;

import java.util.Objects;


public abstract class AbstractLogSink implements LogSink
{
    // CONSTRUCTOR
    private final LogFormatter formatter;

    public AbstractLogSink(
            LogFormatter formatter
    )
    {
        this.formatter = Objects.requireNonNull(
                formatter, "formatter"
        );
    }
    // ~~CONSTRUCTOR~~

    // PROTECTED
    protected String format(
            LogEvent event
    )
    {
        return formatter.format(event);
    }
    // ~~PROTECTED~~
}
