package cat.breadcat.logger;


import cat.breadcat.logger.sink.LogSink;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public final class LoggingSystem implements AutoCloseable
{
    // CONSTRUCTOR
    private static final LoggingSystem INSTANCE = new LoggingSystem();

    private final ExecutorService worker;


    private LoggingSystem()
    {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> LoggingSystem.instance().close())
        );

        this.worker = Executors.newSingleThreadExecutor(runnable ->
        {
            Thread thread = new Thread(runnable, "logger");
            thread.setDaemon(true);

            return thread;
        });
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static LoggingSystem instance()
    {
        return INSTANCE;
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    public void submit(LogTask task)
    {
        worker.execute(() ->
        {
            for(LogSink sink : task.sinks())
            {
                try
                {
                    sink.log(task.event());
                }
                catch(Exception e)
                {
                    System.err.println("A sink failed: " + e);
                }
            }
        });
    }


    @Override
    public void close()
    {
        System.out.println("de blootoofh devaice hase ben disconekted");
        worker.shutdown();

        try
        {
            if(!worker.awaitTermination(5, TimeUnit.SECONDS))
                worker.shutdownNow();
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
            worker.shutdownNow();
        }
    }
    // ~~PUBLIC~~
}
