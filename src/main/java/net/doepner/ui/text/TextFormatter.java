package net.doepner.ui.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public abstract class TextFormatter extends DocumentFilter {

	@Override
	public void insertString(FilterBypass fb, int offset, String text,
			AttributeSet attr) throws BadLocationException {
		super.insertString(fb, offset, format(text), attr);
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {
		super.replace(fb, offset, length, format(text), attrs);
	}
	
	public abstract String format(String text);
}
