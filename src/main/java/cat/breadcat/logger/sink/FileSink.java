package cat.breadcat.logger.sink;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.formatter.LogFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


public final class FileSink extends AbstractLogSink
{
    // IN - EXTERNAL
    // OUT - EXTERNAL (CAPTURED BY PARENT)

    // CONSTRUCTOR
    private final PrintStream out;


    public FileSink(
            LogFormatter formatter, Path filePath
    )
    {
        super(formatter);

        Objects.requireNonNull(
                filePath, "filePath"
        );

        try
        {
            Path parent = filePath.getParent();
            if(parent != null)
                Files.createDirectories(parent);

            this.out = new PrintStream(
                    new FileOutputStream(
                            filePath.toFile(), true
                    ),
                    true,
                    StandardCharsets.UTF_8
            );
        }
        catch(IOException e)
        {
            throw new RuntimeException(
                    "Failed to initialize FileSink for path: " + filePath, e
            );
        }
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC
    @Override
    public void log(
            LogEvent event
    )
    {
        out.println(
                format(event)
        );
    }
    // ~~PUBLIC~~
}
