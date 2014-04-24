package net.doepner.log;

/**
 * Provides a single system console log instance
 */
public class SystemConsoleLogProvider implements LogProvider {

    private final Log log = new SystemConsoleLog();

    @Override
    public Log getLog(Class<?> clazz) {
        return log;
    }
}
