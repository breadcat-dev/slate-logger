# Slate Engine - Logger

![License](https://img.shields.io/badge/License-PolyForm%20Noncommercial%201.0.0-blue)
![Java](https://img.shields.io/badge/Java-21-007396?style=flat&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-alpha-red)
[![Latest Release](https://img.shields.io/github/v/release/breadcat-dev/slate-logger?style=flat&logo=github&color=blue)](https://github.com/breadcat-dev/slate-logger)
[![Downloads](https://img.shields.io/github/downloads/breadcat-dev/slate-logger/total?style=flat&logo=github&color=brightgreen)](https://github.com/breadcat-dev/slate-logger)
[![Stars](https://img.shields.io/github/stars/breadcat-dev/slate-logger?style=flat&logo=github&color=yellow)](https://github.com/breadcat-dev/slate-logger)
> A modular, type-safe logging library, used by the Slate Engine 

---

## Features

- Custom sinks
- Custom formatters
- Custom categories
- Context-aware logging
- ANSI color support
- File logging
- Thread capture
- Dependency-free


## Installation

### Requirements

- Java JDK 21+
- Gradle 9.3.0+ (included)
- Git


Currently, Slate Logger is not on Maven Central.
To use it, clone the repository and publish it to your local Maven Repository.

### Linux / MacOS

```sh
git clone https://github.com/breadcat-dev/slate_logger.git
cd slate_logger
./gradlew publishToMavenLocal
```

### Windows

```sh
git clone https://github.com/breadcat-dev/slate_logger.git
cd slate_logger
./gradlew.bat publishToMavenLocal
```

Once installed, add the dependency:

### Groovy
```gradle
implementation "cat.breadcat:slate_logger:<version>"
```

### Kotlin
```gradle
implementation("cat.breadcat:slate_logger:<version>")
```

---

## Usage

```java
package test.readme;


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
```


## Examples

### Custom Formatter

```java
public final class NetworkFormatter implements LogFormatter
{
    private static final NetworkFormatter INSTANCE = new NetworkFormatter();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());

    private NetworkFormatter() {}


    @Override
    public String format(LogEvent event)
    {
        LogContext context = event.context();

        if(!(context.has("user") && context.has("address")))
            // something happened

        String thread = "";
        if(context.has(LogContextKeys.THREAD_NAME) && context.has(LogContextKeys.THREAD_ID))
        {
            String threadName = context.get(LogContextKeys.THREAD_NAME).toString();
            String threadId = context.get(LogContextKeys.THREAD_ID).toString();
            thread = "-" + (threadName.isBlank() ? "thread" + threadId : threadName);
        }

        String category = "";
        if(context.has(LogContextKeys.CATEGORY))
            category = "-" + context.get(LogContextKeys.CATEGORY).toString();


        String timestamp = this.dateFormatter.format(event.timestamp());
        String className = event.className() + thread;
        String user = context.get("user").toString() + " - " + context.get("address").toString();
        String level = event.level() + category;

        String coloredTimestamp = Ansi.color(timestamp, AnsiColor.CYAN);
        String coloredClassName = Ansi.color(className, AnsiColor.MAGENTA);
        String coloredLevel = Ansi.color(level, event.level().color());
        String coloredUser = Ansi.color(user, AnsiColor.RED);
        String message = event.message();


        return "(" + coloredTimestamp + ") [" + coloredClassName + "] {" + coloredUser + "} <" + coloredLevel + "> " + message;
    }


    public static NetworkFormatter instance()
    {
        return INSTANCE;
    }
}
```

### Custom Context

```java
final Logger LOGGER = Logger.builder()
        .setClassName(Main.class)
        .addSink(new ConsoleSink(NetworkFormatter.instance()))
        .build();

long threadId = Thread.currentThread().threadId();

LOGGER.log(
        Log.error("BreadCat has left the server")
                .category("connection")
                .threadName("network" + threadId)
                .threadId(threadId)
                .with("user", "BreadCat")
                .with("address", "127.0.0.1:25565")
);
```

> (2026-07-25 19:37:41.755) [Main-network1] {BreadCat - 127.0.0.1:25565} <ERROR-connection> BreadCat has left the server


## Roadmap

- init
- "{}" formatting
- exception logging
- final cleanup


## Dependencies

*none*


## License

PolyForm Noncommercial License 1.0.0