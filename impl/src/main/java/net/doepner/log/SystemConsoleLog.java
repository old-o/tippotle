package net.doepner.log;

/**
 * Logs to stdout and stderr
 */
public class SystemConsoleLog implements Log {

    @Override
    public void info(Object o) {
        System.out.println(o);
    }

    @Override
    public void error(Object o) {
        System.err.println(o);
    }

    @Override
    public void debug(String message, Object... parameters) {
        info(message);
        for (Object parameter : parameters) {
            info(parameter);
        }
    }
}
