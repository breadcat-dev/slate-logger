package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;


public final class DiscardSink extends AbstractLogSink
{
    // ===== Constants =====

    private static volatile String BLACK_HOLE;

    // ===== Constructors =====

    public DiscardSink(LogFormatter formatter)
    {
        super(formatter);
    }

    // ===== Logging =====

    @Override
    public void log(LogEvent event)
    {
        BLACK_HOLE = format(event);
    }
}
