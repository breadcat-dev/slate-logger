package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;


public final class DiscardSink extends AbstractLogSink
{
    // IN - EXTERNAL (CAPTURED BY PARENT)
    // OUT - REDUNDANT

    // CONSTRUCTOR
    private static volatile String BLACK_HOLE;

    public DiscardSink(
            LogFormatter formatter
    )
    {
        super(formatter);
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC
    @Override
    public void log(
            LogEvent event
    )
    {
        BLACK_HOLE = format(event);
    }
    // ~~PUBLIC~~
}
