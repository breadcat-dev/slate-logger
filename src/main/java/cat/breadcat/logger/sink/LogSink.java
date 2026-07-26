package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;


public interface LogSink extends AutoCloseable
{
    // PUBLIC
    void log(LogEvent event);
    // ~~PUBLIC~~

    // DEFAULT
    @Override
    default void close() {}
    // ~~DEFAULT~~
}
