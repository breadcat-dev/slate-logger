package cat.breadcat.logger.event;


public final class LogThread
{
    // IN - INTERNAL
    // OUT - EXTERNAL

    // CONSTRUCTOR
    private final Thread thread;


    private LogThread(
            Thread thread
    )
    {
        this.thread = thread;
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
