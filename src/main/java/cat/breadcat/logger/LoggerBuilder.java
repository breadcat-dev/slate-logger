package cat.breadcat.logger;


import cat.breadcat.logger.sink.AbstractLogSink;

import java.util.ArrayList;
import java.util.List;


public final class LoggerBuilder
{
    private final List<AbstractLogSink> sinks;
    private LogLevel minimum;
    private String className;
    private boolean captureThread;

    LoggerBuilder()
    {
        this.sinks = new ArrayList<>();
        this.minimum = LogLevel.DEBUG;
        this.className = "Root";
        this.captureThread = false;
    }


    public LoggerBuilder setClassName(String className)
    {
        this.className = className;
        return this;
    }

    public LoggerBuilder setClassName(Class<?> clazz)
    {
        this.className = clazz.getSimpleName();
        return this;
    }


    public LoggerBuilder addSink(AbstractLogSink sink)
    {
        this.sinks.add(sink);
        return this;
    }


    public LoggerBuilder setMinimum(LogLevel level)
    {
        this.minimum = level;
        return this;
    }


    public LoggerBuilder captureThread()
    {
        this.captureThread = true;
        return this;
    }


    public Logger build()
    {
        return new Logger(
                sinks.toArray(AbstractLogSink[]::new),
                minimum,
                className,
                captureThread
        );
    }
}
