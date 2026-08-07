package cat.breadcat.logger.event;


public final class LogThread
{
    // ===== Fields =====

    private final Thread thread;

    // ===== Constructors =====

    private LogThread(Thread thread)
    {
        this.thread = thread;
    }

    // ===== Factories =====

    public static LogThread capture()
    {
        return new LogThread(Thread.currentThread());
    }

    // ===== Getters =====

    public String name()
    {
        return thread.getName();
    }

    public long id()
    {
        return thread.threadId();
    }
}
