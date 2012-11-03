package net.doepner.ui.text;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;


public abstract class TextListener implements DocumentListener {
	
	public abstract void process(DocumentEvent e, String text);
	
	@Override
	public void insertUpdate(final DocumentEvent e) {
		process(e, getText(e));
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// nothing to do;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// nothing to do yet
	}	
	
	private static String getText(DocumentEvent e) {
		try {
			return e.getDocument().getText(e.getOffset(), e.getLength());
		} catch (BadLocationException ble) {
			throw new IllegalStateException(ble);
		}
	}
}
