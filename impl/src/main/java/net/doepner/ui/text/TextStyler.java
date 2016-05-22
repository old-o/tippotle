package net.doepner.ui.text;

import net.doepner.ui.CharStyler;
import org.guppy4j.log.LogProvider;

import javax.swing.event.DocumentEvent;
import javax.swing.text.StyledDocument;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Sets text attributes (currently mainly colors)
 * only newly inserted text
 */
public final class TextStyler extends DocUpdateAdapter {

    private final CharStyler charStyler;
    private final LogProvider logProvider;

    /**
     * @param charStyler The character styler
     * @param logProvider Log provider
     */
    public TextStyler(CharStyler charStyler, LogProvider logProvider) {
        this.charStyler = charStyler;
        this.logProvider = logProvider;
    }

    @Override
    public void handleUpdate(DocumentEvent event) {
        final StyledDocument doc = (StyledDocument) event.getDocument();
        final int offset = event.getOffset();

        invokeLater(() -> {
            final String text = new DocEvent(logProvider, event).getText();

            for (int i = 0; i < text.length(); i++) {
                doc.setCharacterAttributes(offset + i, 1,
                        charStyler.getAttribs(text.charAt(i)), true);
            }
        });

    }
}
