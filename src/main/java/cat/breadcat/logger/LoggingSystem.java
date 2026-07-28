package cat.breadcat.logger;


import cat.breadcat.logger.sink.LogSink;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;


final class LoggingSystem implements AutoCloseable
{
    // IN - INTERNAL
    // OUT - EXTERNAL

    // CONSTRUCTOR
    private static final LoggingSystem INSTANCE = new LoggingSystem();

    private final ExecutorService worker;
    private final Set<LogSink> sinks = Collections.newSetFromMap(
            new IdentityHashMap<>()
    );


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

    // STATIC
    static LoggingSystem instance()
    {
        return INSTANCE;
    }
    // ~~STATIC~~

    // PACKAGE-PRIVATE
    void submit(LogTask task)
    {
        try
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
                        System.err.println("Failed to log to sink: " + e);
                    }
                }
            });
        }
        catch(RejectedExecutionException e)
        {
            System.err.println("Log dropped during shutdown: " + task.event().message());
        }
    }

    void register(LogSink[] sinks)
    {
        synchronized(this.sinks)
        {
            Collections.addAll(this.sinks, sinks);
        }
    }
    // ~~PACKAGE PRIVATE~~

    // PUBLIC
    @Override
    public void close()
    {
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

        synchronized(sinks)
        {
            for(LogSink sink : sinks)
            {
                try
                {
                    sink.close();
                }
                catch(Exception e)
                {
                    System.err.println("Failed to close sink " + sink + ": " + e);
                }
            }
        }
    }
    // ~~PUBLIC~~
}
