package cat.breadcat.logger;


import cat.breadcat.logger.event.LogEvent;
import cat.breadcat.logger.sink.LogSink;


record LogTask(
        LogEvent event,
        LogSink[] sinks
)
{}