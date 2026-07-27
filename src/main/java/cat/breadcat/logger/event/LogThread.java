package cat.breadcat.logger.event;


import java.util.Objects;


public final class LogThread
{
    // CONSTRUCTOR
    private final Thread thread;

    private LogThread(
            Thread thread
    )
    {
        this.thread = Objects.requireNonNull(
                thread, "thread"
        );
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static LogThread capture()
    {
        return new LogThread(
                Thread.currentThread()
        );
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    public String name()
    {
        return thread.getName();
    }

    public long id()
    {
        return thread.threadId();
    }
    // ~~PUBLIC~~
}
