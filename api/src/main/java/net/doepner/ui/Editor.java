package net.doepner.ui;

import net.doepner.text.TextCoordinates;
import net.doepner.text.TextModel;

/**
 * Abstract text editor
 */
public interface Editor extends TextCoordinates, FontResizable {

    TextModel getTextModel();
}
