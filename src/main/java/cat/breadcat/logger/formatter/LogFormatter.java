package cat.breadcat.logger.formatter;


import cat.breadcat.logger.event.LogEvent;


public interface LogFormatter
{
    // PUBLIC
    String format(LogEvent event);
    // ~~PUBLIC~~
}
