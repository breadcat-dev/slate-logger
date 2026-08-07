package cat.breadcat.logger.event;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;


public final class LogException
{
    // ===== Fields =====

    private final Throwable throwable;

    // ===== Constructors =====

    private LogException(Throwable throwable)
    {
        Objects.requireNonNull(throwable, "throwable");

        this.throwable = throwable;
    }

    // ===== Factories =====

    public static LogException of(Throwable throwable)
    {
        return new LogException(throwable);
    }

    // ===== Getters =====

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
}
