package cat.breadcat.logger.event;


import java.util.HashMap;
import java.util.Map;


public final class LogContext
{
    private final Map<String, Object> context;

    private LogContext(Map<String, Object> context)
    {
        this.context = Map.copyOf(context);
    }


    public static LogContext empty()
    {
        return new LogContext(Map.of());
    }


    public LogContext with(String key, Object value)
    {
        Map<String, Object> context = new HashMap<>(this.context);
        context.put(key, value);

        return new LogContext(context);
    }


    public Object get(String key)
    {
        return this.context.get(key);
    }


    public boolean has(String key)
    {
        return this.context.get(key) != null;
    }
}
