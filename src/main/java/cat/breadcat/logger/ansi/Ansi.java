package cat.breadcat.logger.ansi;


import java.util.Objects;


public final class Ansi
{
    // CONSTRUCTOR
    private Ansi() {}
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static String color(
            String text,
            AnsiColor color
    )
    {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(color, "color");

        return color.code() + text + AnsiColor.RESET.code();
    }
    // ~~PUBLIC STATIC~~
}
