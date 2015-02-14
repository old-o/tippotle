package net.doepner.event;

import java.util.Collection;
import java.util.LinkedList;

import static net.doepner.util.ComparisonUtil.bothNullOrEqual;
import static net.doepner.util.ComparisonUtil.not;

/**
 * Propagates changes of T values to listeners
 *
 * @param <T> The value type
 */
public final class ChangeSupport<T> implements ChangePropagator<T> {

    private final Collection<ChangeListener<T>> listeners = new LinkedList<>();

    @Override
    public void handleChange(T before, T after) {
        if (not(bothNullOrEqual(before, after))) {
            for (ChangeListener<T> listener : listeners) {
                listener.handleChange(before, after);
            }
        }
    }

    @Override
    public void addListener(ChangeListener<T> listener) {
        listeners.add(listener);
    }
}
