package cat.breadcat.logger.ansi;


import java.util.Objects;


public final class Ansi
{
    // CONSTRUCTOR
    private Ansi() {}
    // ~~CONSTRUCTOR~~

    // PUBLIC STATIC
    public static String color(
            String text, AnsiColor color
    )
    {
        return
                Objects.requireNonNull(color, "color").code() +
                Objects.requireNonNull(text, "text") +
                AnsiColor.RESET.code();
    }
    // ~~PUBLIC STATIC~~
}
