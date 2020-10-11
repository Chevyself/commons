package me.googas.commons.log;

import me.googas.commons.CoreFiles;
import me.googas.commons.time.TimeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Factory for loggers
 */
public class LoggerFactory {

    /**
     * Get a file handler which creates files in /logs
     *
     * @param formatter the formatter to use in the handler
     * @param url the path to the file
     * @return the handler created
     * @throws IOException in case the /logs directory could not be created
     */
    @NotNull
    public static FileHandler getFileHandler(@NotNull Formatter formatter, @Nullable String url)
            throws IOException {
        File directory = CoreFiles.directoryOrCreate(CoreFiles.currentDirectory() + "/logs/");
        LocalDateTime date = TimeUtils.getLocalDateFromMillis(System.currentTimeMillis());
        FileHandler handler;
        if (url != null) {
            handler = new FileHandler(url, true);
        } else {
            handler =
                    new FileHandler(
                            directory.toPath().toString()
                                    + File.separator
                                    + date.getMonthValue()
                                    + "-"
                                    + date.getDayOfMonth()
                                    + "-"
                                    + date.getYear()
                                    + " @ "
                                    + date.getHour()
                                    + "-"
                                    + date.getMinute()
                                    + ".txt");
        }
        handler.setFormatter(formatter);
        return handler;
    }

    /**
     * Get a new console handler
     *
     * @param formatter the formatter to use in the handler
     * @return the handler formatted
     */
    @NotNull
    public static ConsoleHandler getConsoleHandler(@NotNull Formatter formatter) {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        return handler;
    }

    /**
     * Start a logger with the provided handlers
     *
     * @param name the name of the logger
     * @param handlers the handlers for the logger
     * @return the logger with the handlers
     */
    @NotNull
    public static Logger start(@NotNull String name, @NotNull Handler... handlers) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        for (Handler handler : handlers) {
            logger.addHandler(handler);
        }
        return logger;
    }

    /**
     * Start a new logger
     *
     * @param name the name of the logger
     * @param formatter the formatter for the handlers
     * @return the logger with console and file handlers
     * @throws IOException in case that the file handler cannot be initialized
     */
    public static Logger start(@NotNull String name, @NotNull Formatter formatter) throws IOException {
        ConsoleHandler console = LoggerFactory.getConsoleHandler(formatter);
        FileHandler fileHandler = LoggerFactory.getFileHandler(formatter, null);
        return LoggerFactory.start(name, console, fileHandler);
    }

}
