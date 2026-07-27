package cat.breadcat.logger.event;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public final class LogContext
{
    // CONSTRUCTOR
    private final Map<String, Object> context;

    private LogContext(
            Map<String, Object> context
    )
    {
        this.context = Map.copyOf(context);
    }
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static LogContext empty()
    {
        return new LogContext(
                Map.of()
        );
    }
    // ~~PUBLIC STATIC~~

    // PUBLIC
    public LogContext with(
            String key, Object value
    )
    {
        Map<String, Object> context = new HashMap<>(this.context);
        context.put(
                Objects.requireNonNull(
                        key, "key"
                ),
                Objects.requireNonNull(
                        value, "value"
                )
        );

        return new LogContext(context);
    }


    public Object get(
            String key
    )
    {
        return context.get(
                Objects.requireNonNull(
                        key, "key"
                )
        );
    }


    public boolean has(
            String key
    )
    {
        return context.containsKey(
                Objects.requireNonNull(
                        key, "key"
                )
        );
    }
    // ~~PUBLIC~~
}
