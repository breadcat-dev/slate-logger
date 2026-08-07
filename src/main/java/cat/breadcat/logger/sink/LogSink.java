package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;


public interface LogSink extends AutoCloseable
{
    // ===== Logging =====

    void log(LogEvent event);

    // ===== Overrides =====

    @Override
    default void close()
    {
    }
}
