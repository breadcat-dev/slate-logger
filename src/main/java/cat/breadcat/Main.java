package cat.breadcat;


import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.Logger;
import cat.breadcat.logger.event.Log;
import cat.breadcat.logger.formatter.ColorFormatter;
import cat.breadcat.logger.formatter.PlainFormatter;
import cat.breadcat.logger.sink.ConsoleSink;
import cat.breadcat.logger.sink.FileSink;

import java.nio.file.Path;


public class Main
{
    public static void main(String[] args)
    {
        // final Logger LOGGER = LoggerFactory.console(Main.class);
        final Logger LOGGER = Logger.builder()
                .setClassName(Main.class)
                .addSink(new ConsoleSink(ColorFormatter.instance()))
                .addSink(new FileSink(PlainFormatter.instance(), Path.of("./debug.log")))
                .setMinimum(LogLevel.INFO)
                .captureThread()
                .build();

        LOGGER.log(Log.debug("debug"));
        LOGGER.log(Log.info("info"));
        LOGGER.log(Log.warn("warn"));
        LOGGER.log(Log.error("error").with("custom_context", 1337));
        LOGGER.log(Log.critical("critical").category("pc-go-boom"));
    }
}