<h1 align="center">Slate Engine - Logger</h1>

<p align="center">
  <img src="icon.png" width="128">
</p>

![License](https://img.shields.io/badge/License-PolyForm%20Noncommercial%201.0.0-blue)
![Java](https://img.shields.io/badge/Java-21-007396?style=flat&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-beta-orange)
[![Latest Release](https://img.shields.io/github/v/release/breadcat-dev/slate-logger?style=flat&logo=github&color=blue)](https://github.com/breadcat-dev/slate-logger)
[![Downloads](https://img.shields.io/github/downloads/breadcat-dev/slate-logger/total?style=flat&logo=github&color=brightgreen)](https://github.com/breadcat-dev/slate-logger)
[![Stars](https://img.shields.io/github/stars/breadcat-dev/slate-logger?style=flat&logo=github&color=yellow)](https://github.com/breadcat-dev/slate-logger)
> A modular, dependency-free, type-safe logging library powering the Slate Engine

---

## Features

- Dependency-free
- Custom sinks
- Custom formatters
- `{}` formatting
- Async logging
- Exception logging
- Context-aware logging
- ANSI color support
- Thread capture
- File logging
- Custom categories


## Installation

### Requirements

- Java JDK 21+
- Gradle 9.3.0+ (included)
- Git

Currently, Slate Logger is not on Maven Central.
To use it, clone the repository and publish it to your local Maven repository.

### Linux / MacOS

```sh
git clone https://github.com/breadcat-dev/slate-logger.git
cd slate-logger
./gradlew publishToMavenLocal
```

### Windows

```sh
git clone https://github.com/breadcat-dev/slate-logger.git
cd slate-logger
./gradlew.bat publishToMavenLocal
```

Once installed, add the dependency:

### Groovy
```gradle
implementation "cat.breadcat.slate:logger:<version>"
```

### Kotlin
```gradle
implementation("cat.breadcat.slate:logger:<version>")
```

---

## Usage

```java
import cat.breadcat.logger.LogLevel;
import cat.breadcat.logger.Logger;
import cat.breadcat.logger.formatter.ColorFormatter;
import cat.breadcat.logger.formatter.PlainFormatter;
import cat.breadcat.logger.sink.ConsoleSink;
import cat.breadcat.logger.sink.FileSink;

import java.io.IOException;
import java.nio.file.Path;


public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        // final Logger LOGGER = LoggerFactory.console(Main.class);

        final Logger LOGGER = Logger.builder()
                .source(Main.class)
                .addSink(new ConsoleSink(ColorFormatter.instance()))
                .addSink(new FileSink(PlainFormatter.instance(), Path.of("./debug.log")))
                .setMinimum(LogLevel.INFO)
                .captureThread()
                .build();

        // Simple logging
        LOGGER.debug("hello");
        LOGGER.info("hello");
        LOGGER.warn("hello");
        LOGGER.error("hello");
        LOGGER.critical("hello");

        // Exception logging + message formatting
        final IOException exception = new IOException("test exception");

        LOGGER.atDebug()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atInfo()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atWarn()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);
        LOGGER.atError()
                .category("category")
                .exception(exception)
                .log("{} was here {} ago", "BreadCat", null);

        // Different thread
        Thread.startVirtualThread(() ->
        {
            LOGGER.atCritical()
                    .category("crash")
                    .exception(exception)
                    .log("something crash related idk");
        });

        Thread.sleep(250);
    }
}
```

## Examples

### Custom Formatter

```java
public final class NetworkFormatter implements LogFormatter
{
    // ===== Constants =====

    private static final NetworkFormatter INSTANCE = new NetworkFormatter();

    // ===== Constructors =====

    private NetworkFormatter()
    {
    }

    // ===== Factories =====

    public static NetworkFormatter instance()
    {
        return INSTANCE;
    }

    // ===== Formatting =====

    @Override
    public String format(LogEvent event)
    {
        Objects.requireNonNull(event.context(), "context");
        Objects.requireNonNull(event.timestamp(), "timestamp");
        Objects.requireNonNull(event.clazz(), "clazz");
        Objects.requireNonNull(event.level(), "level");
        Objects.requireNonNull(event.message(), "message");

        LogContext context = event.context();
        LogThread thread = event.thread();
        LogException exception = event.exception();
        Object category = context.get(LogContextKeys.CATEGORY);
        Object user = context.get("user");
        Object address = context.get("address");
        LogTimestamp timestamp = event.timestamp();
        String className = event.clazz().getSimpleName();
        LogLevel level = event.level();
        String message = event.message();

        String formattedThread =
                (thread != null) ?
                        "-" + (thread.name().isBlank() ? "thread" + thread.id() : thread.name()) :
                        "";
        String formattedException =
                (exception != null) ?
                        "\n" + exception.stackTrace() :
                        "";
        String formattedCategory =
                (category != null) ?
                        "-" + category :
                        "";
        String formattedUser = user + "-" + address;
        String formattedTimestamp = timestamp.format();
        String formattedClassName = className + formattedThread;
        String formattedLevel = level + formattedCategory;

        String coloredException = Ansi.color(formattedException, level.color());
        String coloredUser = Ansi.color(formattedUser, AnsiColor.YELLOW);
        String coloredTimestamp = Ansi.color(formattedTimestamp, AnsiColor.CYAN);
        String coloredClassName = Ansi.color(formattedClassName, AnsiColor.MAGENTA);
        String coloredLevel = Ansi.color(formattedLevel, level.color());

        return "(" + coloredTimestamp + ") [" + coloredClassName + "] {" + coloredUser + "} <" + coloredLevel + "> " + message + coloredException;
    }
}
```

### Custom Context

```java
final Logger LOGGER = Logger.builder()
        .source(Main.class)
        .addSink(new ConsoleSink(NetworkFormatter.instance()))
        .captureThread()
        .build();

LOGGER.atInfo()
        .with("user", "BreadCat")
        .with("address", "127.0.0.1")
        .category("connection")
        .log("A new user has connected to the server");
```

**Console:**

```text
(2026-08-07 14:13:41.706) [Main-main] {BreadCat-127.0.0.1} <INFO-connection> A new user has connected to the server
```

## Roadmap

- Performance optimization
- Log rotation

## Dependencies

*none*

## License

PolyForm Noncommercial License 1.0.0