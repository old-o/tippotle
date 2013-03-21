package net.doepner.event;

/**
 * Change listener
 */
public interface ChangeListener<T> {

    void handleChange(T before, T after);
}
