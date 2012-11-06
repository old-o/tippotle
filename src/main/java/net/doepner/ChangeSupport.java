package net.doepner;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Helper class for change event propagation
 */
public class ChangeSupport implements ChangeNotifier, ChangeListener {

    private final Collection<ChangeListener> listeners = new LinkedList<>();

    @Override
    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void handleChange() {
        for (ChangeListener listener : listeners) {
            listener.handleChange();
        }
    }
}
