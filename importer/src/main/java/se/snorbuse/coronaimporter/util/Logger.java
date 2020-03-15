package se.snorbuse.coronaimporter.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Logger {

    private static Level logLevel = Level.INFO;
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.ms");

    public static void setLevel(Level level) {
        logLevel = level;
    }

    public static void info(Object object) {
        printLog("INFO", object.toString());
    }

    public static void info(String format, Object... args) {
        printLog("INFO", String.format(format, args));
    }

    public static void debug(String format, Object... args) {
        if (logLevel == Level.DEBUG) {
            printLog("DEBUG", String.format(format, args));
        }
    }

    private static void printLog(String loglevel, String logMessage) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[3].getClassName();

        LocalDateTime now = LocalDateTime.now();
        System.out.println(String.format(
                "[%-24s][%-5s][%40s] - %s",
                now.format(DATE_FORMAT),
                loglevel,
                className.substring(Math.max(0, className.length() - 40)),
                logMessage
        ));
    }

    public static enum Level {
        INFO, DEBUG,
    }
}
