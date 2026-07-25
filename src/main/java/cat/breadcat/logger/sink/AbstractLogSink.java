package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;


public abstract class AbstractLogSink implements LogSink
{
    private final LogFormatter formatter;

    public AbstractLogSink(LogFormatter formatter)
    {
        this.formatter = formatter;
    }


    protected String format(LogEvent event)
    {
        return this.formatter.format(event);
    }
}
