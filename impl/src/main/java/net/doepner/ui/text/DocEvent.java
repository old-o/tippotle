package net.doepner.ui.text;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

import static org.guppy4j.log.Log.Level.debug;

/**
 * Utility methods for document events
 */
public final class DocEvent {

    private final Log log;

    private final DocumentEvent event;

    public DocEvent(LogProvider logProvider, DocumentEvent event) {
        log = logProvider.getLog(getClass());
        this.event = event;
    }

    public String getText() {
        try {
            return event.getDocument().getText(event.getOffset(), event.getLength());
        } catch (BadLocationException e) {
            log.as(debug, e);
            return "";
        }
    }
}
