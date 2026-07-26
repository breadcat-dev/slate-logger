package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;


public final class ConsoleSink extends AbstractLogSink
{
    // CONSTRUCTOR
    public ConsoleSink(
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
        System.out.println(
                format(event)
        );
    }
    // ~~PUBLIC~~
}
