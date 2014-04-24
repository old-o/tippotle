package net.doepner.log;

/**
 * Log abstraction
 */
public interface Log {

    void info(Object o);

    void error(Object o);

    void debug(String message, Object... parameters);
}
