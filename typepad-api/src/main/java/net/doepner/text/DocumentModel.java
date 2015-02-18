package net.doepner.text;

import net.doepner.ui.text.DocSwitchListener;

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
}
