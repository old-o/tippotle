package net.doepner.util;

/**
 * Utility methods for object comparison
 */
public class ComparisonUtil {

    public static boolean not(boolean b) {
        return !b;
    }

    public static <T> boolean bothNullOrEqual(T t1, T t2) {
        return t1 == null ? t2 == null : t1.equals(t2);
    }
}
