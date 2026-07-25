package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public final class FileSink extends AbstractLogSink
{
    private final PrintStream out;

    public FileSink(LogFormatter formatter, Path file)
    {
        super(formatter);

        try
        {
            Path parent = file.getParent();
            if (parent != null)
                Files.createDirectories(parent);

            this.out = new PrintStream(
                    new FileOutputStream(file.toFile(), false),
                    true,
                    StandardCharsets.UTF_8
            );
        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed to initialize FileSink for path: " + file, e);
        }
    }

    @Override
    public void log(LogEvent event)
    {
        out.println(format(event));
    }
}
