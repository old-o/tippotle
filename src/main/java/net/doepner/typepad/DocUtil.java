package net.doepner.typepad;

import javax.swing.event.DocumentEvent;
import javax.swing.text.DefaultStyledDocument;

import net.doepner.speech.Speaker;
import net.doepner.ui.text.TextListener;
import net.doepner.ui.text.TextStyler;

public class DocUtil {
	
	static DefaultStyledDocument prepareDocument(
			final Speaker speaker) {

		final DefaultStyledDocument doc = new DefaultStyledDocument();
						
		doc.addDocumentListener(new TextStyler(
                new AlphaNumStyler()));

		doc.addDocumentListener(new TextListener() {
			@Override
			public void process(DocumentEvent e, String text) {
                if (!text.trim().isEmpty()) {
				    speaker.speak(text);
                }
			}
		});
		
		return doc;
	}
}
