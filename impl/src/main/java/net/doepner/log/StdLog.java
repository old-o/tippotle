package net.doepner.log;

/**
 * Logs to stdout and stderr
 */
public class StdLog implements Log {

    @Override
    public void info(Object o) {
        System.out.println(o);
    }

    @Override
    public void error(Object o) {
        System.err.println(o);
    }
}
