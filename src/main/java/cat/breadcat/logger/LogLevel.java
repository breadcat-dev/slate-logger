package cat.breadcat.logger;


import cat.breadcat.logger.ansi.AnsiColor;


public enum LogLevel
{
    // ===== Constants =====

    DEBUG(AnsiColor.BRIGHT_MAGENTA, 0),
    INFO(AnsiColor.BRIGHT_BLUE, 1),
    WARN(AnsiColor.YELLOW, 2),
    ERROR(AnsiColor.BRIGHT_RED, 3),
    CRITICAL(AnsiColor.RED, 4);

    // ===== Fields =====

    private final AnsiColor color;
    private final int priority;

    // ===== Constructors =====

    LogLevel(AnsiColor color, int priority)
    {
        this.color = color;
        this.priority = priority;
    }

    // ===== Queries =====

    public boolean isAtLeast(LogLevel level)
    {
        return this.priority >= level.priority;
    }

    // ===== Getters =====

    public AnsiColor color()
    {
        return this.color;
    }
}

