package net.doepner.util;

/**
 * Collects items to later perform a certain action on all of them
 */
public interface ActionableItems<T> {

    void add(T t);

    void actOnAll();

}
