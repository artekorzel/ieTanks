package pl.edu.agh.ietanks.engine.util;

import java.util.logging.Logger;

/**
 * Decorates Runnable with exception logging
 */
public class LogExceptionRunnable implements Runnable {
    private final Runnable runnable;
    private static Logger LOGGER = Logger.getLogger(LogExceptionRunnable.class.getName());

    public LogExceptionRunnable(Runnable runnable) {
        this.runnable = runnable;
    }


    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Throwable e) {
            LOGGER.severe(e.toString());
            e.printStackTrace();
        }
    }
}
