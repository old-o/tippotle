package net.doepner.util;

/**
 * Utility methods for object comparison
 */
public class ComparisonUtil {

    public static <T> boolean bothNullOrEqual(T before, T after) {
        return before == null ? after == null : before.equals(after);
    }
}
