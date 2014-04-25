package net.doepner.log;

/**
 * Logs to stdout and stderr
 */
public class SystemConsoleLog implements Log {

    @Override
    public void $(Level level, String message, Throwable t) {
        System.err.println(message);
        t.printStackTrace();
    }

    @Override
    public void $(Level level, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void $(Level level, String message, Object... parameters) {
        System.out.println(message);
        for (Object parameter : parameters) {
            System.out.println(parameter);
        }
    }
}
