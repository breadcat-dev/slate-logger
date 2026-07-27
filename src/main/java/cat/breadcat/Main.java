package cat.breadcat;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.Logger;
import cat.breadcat.logger.formatter.ColorFormatter;
import cat.breadcat.logger.formatter.PlainFormatter;
import cat.breadcat.logger.sink.ConsoleSink;
import cat.breadcat.logger.sink.FileSink;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;


public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        // final Logger LOGGER = LoggerFactory.console(Main.class);

        final Logger LOGGER = Logger.builder()
                .setClassName(Main.class)
                .addSink(
                        new ConsoleSink(
                                ColorFormatter.instance()
                        )
                )
                .addSink(
                        new FileSink(
                                PlainFormatter.instance(),
                                Path.of("./debug.log")
                        )
                )
                .setMinimum(LogLevel.DEBUG)
                .captureThread()
                .build();


        // Simple logging
        LOGGER.info("hello");

        // Getting a test exception (YOU CAN IGNORE THIS PART, I KNOW IT S UGLY)
        IOException exception = new IOException();
        try(FileInputStream fileInputStream = new FileInputStream("./tz"))
        {

        }
        catch(IOException e)
        {
            exception = e;
        }
        final IOException finalException = exception; // Needed for lambda

        // Exception logging + message formatting
        LOGGER.atError()
                .category("crash")
                .exception(exception)
                .log("test message by {} from {} ago", "BreadCat", null);

        // Different thread
        Thread.startVirtualThread(() ->
        {
            LOGGER.atWarn()
                    .category("possible-crash")
                    .exception(finalException)
                    .log("A possible crash may happen");
        });

        // Sleep so the other thread has a chance to log
        try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        /*for(int i = 0; i < 100; i++)
        {
            Thread.ofVirtual()
                    .name("test" + i)
                    .start(() ->
            {
                Logger logger = Logger.builder()
                        .setClassName(Main.class)
                        .addSink(new ConsoleSink(ColorFormatter.instance()))
                        .captureThread()
                        .build();

                for(int j = 0; j < 100; j++)
                    logger.info("{}", j);
            });
        }*/
    }
}