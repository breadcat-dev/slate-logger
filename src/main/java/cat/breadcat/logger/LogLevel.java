package cat.breadcat.logger;


import cat.breadcat.logger.ansi.AnsiColor;


public enum LogLevel
{
    DEBUG(AnsiColor.BRIGHT_MAGENTA, 0),
    INFO(AnsiColor.BRIGHT_BLUE, 1),
    WARN(AnsiColor.YELLOW, 2),
    ERROR(AnsiColor.BRIGHT_RED, 3),
    CRITICAL(AnsiColor.RED, 4);


    private final AnsiColor color;
    private final int priority;

    LogLevel(AnsiColor color, int priority)
    {
        this.color = color;
        this.priority = priority;
    }


    public AnsiColor color()
    {
        return this.color;
    }


    public boolean isAtLeast(LogLevel level)
    {
        return this.priority >= level.priority;
    }
}

