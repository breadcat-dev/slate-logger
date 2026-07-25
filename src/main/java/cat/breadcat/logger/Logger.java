package cat.breadcat.logger;


import cat.breadcat.logger.event.Log;
import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.sink.AbstractLogSink;

import java.time.Instant;


public final class Logger
{
    private final AbstractLogSink[] sinks;
    private final LogLevel minimum;
    private final String className;
    private final boolean captureThread;

    Logger(AbstractLogSink[] sinks, LogLevel minimum, String className, boolean captureThread)
    {
        this.sinks = sinks;
        this.minimum = minimum;
        this.className = className;
        this.captureThread = captureThread;
    }


    public static LoggerBuilder builder()
    {
        return new LoggerBuilder();
    }


    public void log(Log log)
    {
        if(!log.level().isAtLeast(minimum))
            return;

        if(captureThread)
        {
            Thread thread = Thread.currentThread();

            log = log
                    .threadName(thread.getName())
                    .threadId(thread.threadId());
        }

        LogEvent event = new LogEvent(
                log.context(),
                Instant.now(),
                this.className,
                log.level(),
                log.message()
        );

        for(AbstractLogSink sink : sinks)
            sink.log(event);
    }
}
