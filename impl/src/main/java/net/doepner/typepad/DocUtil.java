package net.doepner.typepad;

import javax.swing.event.DocumentEvent;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

import net.doepner.ui.text.AlphaNumStyler;
import net.doepner.ui.text.TextChangeListener;
import net.doepner.ui.text.TextStyler;

/**
 * Prepares the document object with listeners
 */
public class DocUtil {

    static StyledDocument createDocument(final IContext context) {

        final StyledDocument doc = new DefaultStyledDocument();

        doc.addDocumentListener(new TextStyler(new AlphaNumStyler()));

        doc.addDocumentListener(new TextChangeListener() {
            @Override
            public void handleChange(DocumentEvent e) {
                context.getSpeaker().speak(getText(e));
            }
        });

        return doc;
    }
}
