package net.doepner.log;

import org.slf4j.Logger;

/**
 * Logs using an slf4j logger
 */
public class Slf4jLog implements Log {

    private final Logger logger;

    public Slf4jLog(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void $(Level level, String message, Object... parameters) {
        switch (level) {
            case trace:
                logger.trace(message, parameters);
                break;
            case debug:
                logger.debug(message, parameters);
                break;
            case info:
                logger.info(message, parameters);
                break;
            case warn:
                logger.warn(message, parameters);
                break;
            case error:
                logger.error(message, parameters);
                break;
        }
    }

    @Override
    public void $(Level level, Throwable t) {
        $(level, "", t);
    }

    @Override
    public void $(Level level, String message, Throwable t) {
        switch (level) {
            case trace:
                logger.trace(message, t);
                break;
            case debug:
                logger.debug(message, t);
                break;
            case info:
                logger.info(message, t);
                break;
            case warn:
                logger.warn(message, t);
                break;
            case error:
                logger.error(message, t);
                break;
        }
    }
}
