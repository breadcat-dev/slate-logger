package cat.breadcat.logger.event;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;


public final class LogException
{
    // CONSTRUCTOR
    private final Throwable throwable;

    public LogException(
            Throwable throwable
    )
    {
        this.throwable = Objects.requireNonNull(
                throwable,
                "throwable"
        );
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC
    public String message()
    {
        return throwable.getMessage();
    }

    public String stackTrace()
    {
        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);

        throwable.printStackTrace(printer);

        return writer.toString();
    }
    // ~~PUBLIC~~
}
