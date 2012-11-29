package net.doepner.text;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import static java.lang.Math.min;
import static net.doepner.text.CharCondition.IS_NUMBER_PART;
import static net.doepner.text.CharCondition.IS_WORD_PART;

public class WordExtractor implements TextProvider {

	private final TextModel model;

	public WordExtractor(TextModel model) {
		this.model = model;
	}

    public WordExtractor(final DocumentEvent e) {
        this(new TextModel() {
            @Override
            public int getOffset() {
                return min(e.getOffset(), getText().length());
            }

            @Override
            public String getText() {
                try {
                    final Document doc = e.getDocument();
                    return doc.getText(0, doc.getLength());
                } catch (BadLocationException ble) {
                    return "";
                }
            }
        });
    }

    @Override
	public String getText() {
		final String word = findSequence(IS_WORD_PART);
		if (word.length() > 0) {
			return word;			
		} else {
			return findSequence(IS_NUMBER_PART);
		}
	}

	private String findSequence(CharCondition cond) {
		final int start = getStart(cond);
		final int end = getEnd(cond);

		return model.getText().substring(start, end);
	}

	private int getStart(CharCondition cond) {
        final String text = model.getText();

        int pos = model.getOffset();
        while (pos > 0 && cond.matches(text.charAt(pos - 1))) {
			pos--;
		}
		return pos;
	}

	private int getEnd(CharCondition cond) {
        final String text = model.getText();

		int pos = model.getOffset();
		while (pos < text.length() && cond.matches(text.charAt(pos))) {
			pos++;
		}
		return pos;
	}
}
