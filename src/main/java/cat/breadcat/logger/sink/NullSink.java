package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;


public final class NullSink implements LogSink
{
    // PUBLIC
    @Override
    public void log(LogEvent event) {}
    // ~~PUBLIC~~
}
