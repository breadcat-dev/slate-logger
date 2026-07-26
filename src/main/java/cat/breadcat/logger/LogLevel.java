package cat.breadcat.logger;


import cat.breadcat.logger.ansi.AnsiColor;

import java.util.Objects;


public enum LogLevel
{
    // DECLARATION
    DEBUG(AnsiColor.BRIGHT_MAGENTA, 0),
    INFO(AnsiColor.BRIGHT_BLUE, 1),
    WARN(AnsiColor.YELLOW, 2),
    ERROR(AnsiColor.BRIGHT_RED, 3),
    CRITICAL(AnsiColor.RED, 4);
    // ~~DECLARATION~~

    // CONSTRUCTOR
    private final AnsiColor color;
    private final int priority;

    LogLevel(
            AnsiColor color,
            int priority
    )
    {
        this.color = color;
        this.priority = priority;
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC
    public AnsiColor color()
    {
        return this.color;
    }


    public boolean isAtLeast(
            LogLevel level
    )
    {
        Objects.requireNonNull(level, "level");

        return this.priority >= level.priority;
    }
    // ~~PUBLIC~~
}

