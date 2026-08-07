package cat.breadcat.logger.formatter;


import cat.breadcat.logger.event.LogEvent;


public interface LogFormatter
{
    // ===== Formatting =====

    String format(LogEvent event);
}
