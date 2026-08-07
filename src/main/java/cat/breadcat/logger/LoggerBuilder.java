package cat.breadcat.logger;


import cat.breadcat.logger.sink.LogSink;

import java.util.ArrayList;
import java.util.List;


public final class LoggerBuilder
{
    // ===== Fields =====
    private final List<LogSink> sinks;
    private LogLevel minimum;
    private Class<?> clazz;
    private boolean captureThread;

    // ===== Constructors =====

    LoggerBuilder()
    {
        this.sinks = new ArrayList<>();
        this.minimum = LogLevel.DEBUG;
        this.clazz = null;
        this.captureThread = false;
    }

    // ===== Configuration =====

    public LoggerBuilder source(Class<?> clazz)
    {
        this.clazz = clazz;

        return this;
    }

    public LoggerBuilder addSink(LogSink sink)
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

    // ===== Building =====

    public Logger build()
    {
        return new Logger(sinks.toArray(LogSink[]::new), minimum, clazz, captureThread);
    }
}
