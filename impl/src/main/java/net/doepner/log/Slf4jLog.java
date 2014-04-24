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
    public void info(Object o) {
        logger.info(o.toString());
    }

    @Override
    public void error(Object o) {
        logger.error(o.toString());
    }

    @Override
    public void debug(String message, Object... parameters) {
        logger.debug(message, parameters);
    }
}
