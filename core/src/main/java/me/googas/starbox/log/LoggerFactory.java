package me.googas.starbox.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import lombok.NonNull;
import me.googas.starbox.CoreFiles;

/** Factory for loggers */
public class LoggerFactory {

  /**
   * Get a file handler which creates files in /logs
   *
   * @param formatter the formatter to use in the handler
   * @param url the path to the file
   * @param fileName the name of the file where the log will be saved
   * @return the handler created
   * @throws IOException in case the /logs directory could not be created
   */
  @NonNull
  public static FileHandler getFileHandler(
      @NonNull Formatter formatter, String url, String fileName) throws IOException {
    File directory = CoreFiles.directoryOrCreate(CoreFiles.currentDirectory() + "/logs/");
    FileHandler handler;
    if (url != null) {
      handler = new FileHandler(url, true);
    } else {
      handler =
          new FileHandler(
              directory.toPath().toString()
                  + File.separator
                  + (fileName == null ? "log" : fileName)
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
  @NonNull
  public static ConsoleHandler getConsoleHandler(@NonNull Formatter formatter) {
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
  @NonNull
  public static Logger start(@NonNull String name, @NonNull Handler... handlers) {
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
   * @param fileName the name of the file were the log will be written
   * @return the logger with console and file handlers
   * @throws IOException in case that the file handler cannot be initialized
   */
  public static Logger start(@NonNull String name, @NonNull Formatter formatter, String fileName)
      throws IOException {
    ConsoleHandler console = LoggerFactory.getConsoleHandler(formatter);
    FileHandler fileHandler = LoggerFactory.getFileHandler(formatter, null, fileName);
    return LoggerFactory.start(name, console, fileHandler);
  }
}
