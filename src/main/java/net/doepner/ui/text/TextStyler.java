package net.doepner.ui.text;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.StyledDocument;

public class TextStyler extends TextListener {
	
	private final CharStyler charStyler;
	
	public TextStyler(CharStyler charStyler) {
		this.charStyler = charStyler;
	}

	@Override
	public void process(DocumentEvent event, final String text) {		
		final StyledDocument doc = (StyledDocument) event.getDocument();
		
		final int offset = event.getOffset();
		final int length = event.getLength();
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < length; i++) {
                    doc.setCharacterAttributes(offset + i, 1,
                            charStyler.getAttribs(text.charAt(i)), true);
                }
            }
        });
	}
}
