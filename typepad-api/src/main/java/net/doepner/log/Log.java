package net.doepner.log;

/**
 * Log abstraction
 */
public interface Log {

    enum Level {
        trace, debug, info, warn, error
    }

    void as(Level level, Throwable t);

    void as(Level level, String message, Throwable t);

    void as(Level level, String message, Object... parameters);
}
