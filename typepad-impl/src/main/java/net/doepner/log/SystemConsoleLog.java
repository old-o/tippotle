package net.doepner.log;

/**
 * Logs to stdout and stderr
 */
public class SystemConsoleLog implements Log {

    @Override
    public void as(Level level, String message, Throwable t) {
        System.err.println(message);
        error(t);
    }

    @Override
    public void error(Throwable t) {
        t.printStackTrace(System.err);
    }

    @Override
    public void as(Level level, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void as(Level level, String message, Object... parameters) {
        System.out.println(message);
        for (Object parameter : parameters) {
            System.out.println(parameter);
        }
    }
}
