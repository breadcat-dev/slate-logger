package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;


public final class NullSink implements LogSink
{
    // IN - REDUNDANT
    // OUT - REDUNDANT

    // PUBLIC
    @Override
    public void log(LogEvent event) {}
    // ~~PUBLIC~~
}
