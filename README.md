<h1 align="center">Slate Engine - Logger</h1>

<p align="center">
  <img src="icon.png" width="128">
</p>

![License](https://img.shields.io/badge/License-PolyForm%20Noncommercial%201.0.0-blue)
![Java](https://img.shields.io/badge/Java-21-007396?style=flat&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-alpha-red)
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
To use it, clone the repository and publish it to your local Maven Repository.

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
implementation "cat.breadcat:slate-logger:<version>"
```

### Kotlin
```gradle
implementation("cat.breadcat:slate-logger:<version>")
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

        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warn("warn");
        LOGGER.atError()
                .with("custom-context", 1337)
                .log("error");
        LOGGER.atCritical()
                .category("boom")
                .log("critical");
        

        for(int i = 0; i < 100; i++)
        {
            Thread.ofVirtual()
                    .name("test" + i)
                    .start(() ->
                    {
                        Logger logger = Logger.builder()
                                .setClassName(Main.class)
                                .addSink(new ConsoleSink(ColorFormatter.instance()))
                                .captureThread()
                                .build();

                        for(int j = 0; j < 100; j++)
                            logger.info("{}", j);
                    });
        }
    }
}
```


## Examples

### Custom Formatter

```java
public final class NetworkFormatter implements LogFormatter
{
    private static final NetworkFormatter INSTANCE = new NetworkFormatter();

    private NetworkFormatter() {}



    public static NetworkFormatter instance()
    {
        return INSTANCE;
    }



    @Override
    public String format(LogEvent event)
    {
        LogContext context = event.context();
        LogThread thread = event.thread();
        LogException exception = event.exception();

        Object category = context.get(
                LogContextKeys.CATEGORY
        );

        Object user = context.get("user");
        Object address = context.get("address");

        LogTimestamp timestamp = event.timestamp();
        String className = event.className();
        LogLevel level = event.level();
        String message = event.message();


        String formattedThread = "";
        if(thread != null)
        {
            String threadName = thread.name();

            formattedThread =
                    "-" +
                            ((threadName.isBlank()) ?
                                    "thread" + thread.id() :
                                    threadName);
        }

        String formattedException = "";
        if(exception != null)
        {
            formattedException =
                    "\n" +
                            exception.stackTrace();
        }

        String formattedCategory = "";
        if(category != null)
        {
            formattedCategory =
                    "-" +
                            category;
        }

        String formattedUser = user + "-" + address;

        String formattedTimestamp = timestamp.format();
        String formattedClassName = className + formattedThread;
        String formattedLevel = level.toString() + formattedCategory;


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
        .setClassName(Main.class)
        .addSink(
                new ConsoleSink(
                        NetworkFormatter.instance()
                )
        )
        .build();

        LOGGER.atError()
                .category("crash")
                .with("user", "BreadCat")
                .with("address", "127.0.0.1")
                .log("A user has crashed.");
```

### Console

```text
(2026-07-27 18:46:38.765) [Main] {BreadCat-127.0.0.1} <ERROR-crash> A user has crashed.
```


## Roadmap

- optimize
- log rotation
- final API cleanup


## Dependencies

*none*


## License

PolyForm Noncommercial License 1.0.0