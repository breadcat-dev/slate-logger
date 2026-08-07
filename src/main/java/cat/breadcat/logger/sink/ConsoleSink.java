package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;


public final class ConsoleSink extends AbstractLogSink
{
    // ===== Constructors =====

    public ConsoleSink(LogFormatter formatter)
    {
        super(formatter);
    }

    // ===== Logging =====

    @Override
    public void log(LogEvent event)
    {
        System.out.println(format(event));
    }
}
