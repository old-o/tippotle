package net.doepner.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Adapter between document events and text change
 * events
 */
public class TextChangeListener implements DocumentListener {

    private final TextChangeHandler handler;

    public TextChangeListener(TextChangeHandler handler) {
        this.handler = handler;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handler.handleChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handler.handleChange(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // nothing to do
    }
}
