package cat.breadcat.logger.event;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public final class LogContext
{
    // ===== Fields =====

    private final Map<String, Object> context;

    // ===== Constructors =====

    private LogContext(Map<String, Object> context)
    {
        this.context = context;
    }

    // ===== Factories =====

    public static LogContext empty()
    {
        return new LogContext(Map.of());
    }

    // ===== Setters =====

    public LogContext with(String key, Object value)
    {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");

        Map<String, Object> context = new HashMap<>(this.context);
        context.put(key, value);

        return new LogContext(context);
    }

    // ===== Queries =====

    public boolean has(String key)
    {
        return context.containsKey(Objects.requireNonNull(key, "key"));
    }

    // ===== Getters =====

    public Object get(String key)
    {
        return context.get(Objects.requireNonNull(key, "key"));
    }
}
