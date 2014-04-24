package net.doepner.log;

/**
 * Provides log instance
 */
public interface LogProvider {

    Log getLog(Class<?> clazz);
}
