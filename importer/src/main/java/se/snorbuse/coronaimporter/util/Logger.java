package se.snorbuse.coronaimporter.util;

public class Logger {
    public static void info(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
