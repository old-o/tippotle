package net.doepner.ui;

import javax.swing.Action;

/**
 * Anything that can have associated Swing actions,
 * usually a Swing component
 */
public interface ActionTarget {

    void addAction(Action action);

}
