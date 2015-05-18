package net.doepner.ui.text;

import javax.swing.text.Document;

/**
 * Listens for documents switches
 */
public interface DocSwitchListener {

    /**
     * @param document The current document (after the switch)
     */
    void docSwitched(Document document);
}
