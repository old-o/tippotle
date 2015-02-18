package net.doepner.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

/**
 * Utility methods for document events
 */
public final class DocEvents {

    private DocEvents() {
        // no instances
    }

    static String getText(DocumentEvent e) {
        try {
            return e.getDocument().getText(e.getOffset(), e.getLength());
        } catch (BadLocationException ble) {
            return "";
        }
    }
}
