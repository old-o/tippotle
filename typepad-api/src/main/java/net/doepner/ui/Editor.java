package net.doepner.ui;

import net.doepner.text.TextCoordinates;

import javax.swing.Action;

/**
 * Abstract text editor
 */
public interface Editor extends TextCoordinates, FontResizable {

    void addAction(Action action, int i);

}
