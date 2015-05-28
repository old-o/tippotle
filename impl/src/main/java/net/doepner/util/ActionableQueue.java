package net.doepner.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Performs a fixed action once on all added items
 */
public final class ActionableQueue<T> implements ActionableItems<T> {

    private final Queue<T> items = new ConcurrentLinkedQueue<>();

    private final Consumer<T> action;

    public ActionableQueue(Consumer<T> action) {
        this.action = action;
    }

    @Override
    public void add(T t) {
        items.add(t);
    }

    @Override
    public void actOnAll() {
        for (T t = next(); t != null; t = next()) {
            action.accept(t);
        }
    }

    private T next() {
        return items.poll();
    }
}
