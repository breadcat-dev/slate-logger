package cat.breadcat;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.Logger;
import cat.breadcat.logger.formatter.ColorFormatter;
import cat.breadcat.logger.formatter.PlainFormatter;
import cat.breadcat.logger.sink.ConsoleSink;
import cat.breadcat.logger.sink.FileSink;

import java.io.IOException;
import java.nio.file.Path;


public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        // final Logger LOGGER = LoggerFactory.console(Main.class);

        final Logger LOGGER = Logger.builder()
                .source(Main.class)
                .addSink(new ConsoleSink(ColorFormatter.instance()))
                .addSink(new FileSink(PlainFormatter.instance(), Path.of("./debug.log")))
                .setMinimum(LogLevel.INFO)
                .captureThread()
                .build();

        // Simple logging
        LOGGER.debug("hello");
        LOGGER.info("hello");
        LOGGER.warn("hello");
        LOGGER.error("hello");
        LOGGER.critical("hello");

        // Exception logging + message formatting
        final IOException exception = new IOException("test exception");

        LOGGER.atDebug()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atInfo()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atWarn()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atError()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);

        // Different thread
        Thread.startVirtualThread(() ->
        {
            LOGGER.atCritical()
                    .category("crash")
                    .exception(exception)
                    .log("something crash related idk");
        });

        Thread.sleep(250);
    }
}