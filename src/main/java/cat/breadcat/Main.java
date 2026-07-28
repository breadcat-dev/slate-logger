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

// ===[ HOMEMADE BENCHMARKS ]===
// ~500ns/op for DiscardSink + ColorFormatter (Intel I5 12th gen)
// pretty noice
/*final Logger LOGGER = Logger.builder()
        .setClassName(Main.class)
        .addSink(new DiscardSink(ColorFormatter.instance()))
        .captureThread()
        .build();


for(int warmup = 0; warmup < 10; warmup++)
{
    for(int i = 0; i < 1_000_000; i++)
    {
        LOGGER.info("message");
    }
}

int repeat = 25;
long[] array = new long[repeat] ;
for(int i = 0; i < repeat; i++)
{
    long start = System.nanoTime();

    for(int j = 0; j < 1_000_000; j++)
    {
        LOGGER.info("breadcat");
    }

    long elapsed = System.nanoTime() - start;
    array[i] = elapsed;

    System.out.printf(
            "Run %d: %.2f ns/op%n",
            i,
            (double) elapsed / 1_000_000
    );
}

long average = 0;
for(int i = 0; i < repeat; i++)
{
    average += array[i];
}
average /= repeat;

System.out.printf(
        "Average: %.2f ns/op%n",
        (double)average / 1_000_000
);*/


// ===[ THREAD TEST ]===
// (it survives)
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
}

Thread.sleep(250);
*/