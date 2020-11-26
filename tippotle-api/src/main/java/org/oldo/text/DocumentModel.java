package org.oldo.text;

import org.oldo.ui.CharStyler;
import org.oldo.ui.text.DocSwitchListener;

/**
 * A text provider that also supports document switching
 * and change notifications
 */
public interface DocumentModel extends TextProvider {

    /**
     * Registers a listener
     *
     * @param listener Listener for text insertion events (like typing, pasting)
     */
    void addTextListener(TextListener listener);

    /**
     * Switch to the next document
     */
    void switchDocument();

    /**
     * Registers a listener
     *
     * @param docSwitchListener Listener for document switching events
     */
    void addDocSwitchListener(DocSwitchListener docSwitchListener);

    /**
     * Applies a character styler to a text spam of the current document
     *
     * @param charStyler The character styler
     * @param textSpan The text spam
     */
    void applyStyle(CharStyler charStyler, TextSpan textSpan);
}
