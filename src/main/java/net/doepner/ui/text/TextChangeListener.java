package net.doepner.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Adapter between document events and text change
 * events
 */
public abstract class TextChangeListener implements DocumentListener {

    protected abstract void handleChange(DocumentEvent e);

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleChange(e);
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
