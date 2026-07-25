package cat.breadcat.logger.ansi;


public final class Ansi
{
    private Ansi() {}


    public static String color(String text, AnsiColor color)
    {
        return color.code() + text + AnsiColor.RESET.code();
    }
}
