package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


/**
 * The class Application logger
 */
public class AppLogger {
    public static String LOG_FILE_NAME = "login_activity.log";
    private static final String LOG_DIRECTORY_NAME = "";

    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());


    /**
     * This method initializes the Logger
     */
    public static void initialize() {
        Handler consoleHandler = null;
        Handler fileHandler  = null;
        try{
            AppLogFormatter formatter = new AppLogFormatter();
            consoleHandler = new ConsoleHandler();
            fileHandler = new FileHandler(LOG_FILE_NAME, true);
            fileHandler.setFormatter(formatter);

            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);

            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);
        }catch(IOException exception){
            LOGGER.log(Level.SEVERE, "Error occurred in FileHandler.", exception);
        }
    }


    /**
     *
     * Info
     *
     * @param message  the message
     */
    public static void info(String message) {

        LOGGER.log(Level.INFO,message);
    }


    /**
     *
     * Warning
     *
     * @param message  the message
     */
    public static void warning(String message) {

        LOGGER.log(Level.WARNING,message);
    }


    /**
     *
     * Error
     *
     * @param message  the message
     */
    public static void error(String message) {

        LOGGER.log(Level.SEVERE,message);
    }


    /**
     *
     * Error
     *
     * @param message  the message
     * @param error  the error
     */
    public static void error(String message,Throwable error) {

        String errorMessage = getPrintStatckTrace(error);
        String msg = message + " with details : " + errorMessage;
        LOGGER.log(Level.SEVERE,msg);
    }


    /**
     *
     * Gets the print statck trace
     *
     * @param error  the error
     * @return the print statck trace
     */
    private static String getPrintStatckTrace(Throwable error) {

        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        return exceptionAsString;
    }

}

class AppLogFormatter extends Formatter {

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    @Override

/**
 *
 * Format
 *
 * @param record  the record
 * @return String
 */
    public String format(LogRecord record) {

        StringBuilder builder = new StringBuilder();
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        builder.append("[").append(record.getSourceClassName()).append(".");
        builder.append(record.getSourceMethodName()).append("] - ");
        builder.append("[").append(record.getLevel()).append("] - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }


    /**
     *
     * Gets the head
     *
     * @param h  the h
     * @return the head
     */
    public String getHead(Handler h) {

        return super.getHead(h);
    }


    /**
     *
     * Gets the tail
     *
     * @param h  the h
     * @return the tail
     */
    public String getTail(Handler h) {

        return super.getTail(h);
    }
}