package net.doepner.event;


/**
 * Change notifier
 */
public interface ChangeNotifier<T> {

    void addListener(ChangeListener<T> listener);

}
