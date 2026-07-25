package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;


public interface LogSink extends AutoCloseable
{
    void log(LogEvent event);

    @Override
    default void close()
    {

    }
}
