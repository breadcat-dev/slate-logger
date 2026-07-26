package cat.breadcat.logger;


import cat.breadcat.logger.formatter.ColorFormatter;
import cat.breadcat.logger.formatter.PlainFormatter;
import cat.breadcat.logger.sink.ConsoleSink;
import cat.breadcat.logger.sink.AbstractLogSink;
import cat.breadcat.logger.sink.FileSink;

import java.nio.file.Path;


public final class LoggerFactory
{
    // CONSTRUCTOR
    private LoggerFactory() {}
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static Logger console(
            Class<?> clazz
    )
    {
        return new Logger(
                new AbstractLogSink[]{
                        new ConsoleSink(ColorFormatter.instance())
                },
                LogLevel.DEBUG,
                clazz.getSimpleName(),
                false
        );
    }

    public static Logger file(
            Class<?> clazz,
            Path file
    )
    {
        return new Logger(
                new AbstractLogSink[]{
                        new FileSink(PlainFormatter.instance(), file)
                },
                LogLevel.DEBUG,
                clazz.getSimpleName(),
                false
        );
    }

    public static Logger consoleAndFile(
            Class<?> clazz,
            Path file
    )
    {
        return new Logger(
                new AbstractLogSink[]{
                        new ConsoleSink(ColorFormatter.instance()),
                        new FileSink(PlainFormatter.instance(), file)
                },
                LogLevel.DEBUG,
                clazz.getSimpleName(),
                false
        );
    }
    // ~~PUBLIC STATIC~~
}
