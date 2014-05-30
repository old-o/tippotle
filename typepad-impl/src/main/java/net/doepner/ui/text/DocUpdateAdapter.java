package net.doepner.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Adapter between document events and text change events
 */
public abstract class DocUpdateAdapter implements DocumentListener {

    protected abstract void handleUpdate(DocumentEvent e);

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // nothing to do
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // nothing to do
    }

    protected String getText(DocumentEvent e) {
        try {
            return e.getDocument().getText(e.getOffset(), e.getLength());
        } catch (BadLocationException ble) {
            return "";
        }
    }
}
