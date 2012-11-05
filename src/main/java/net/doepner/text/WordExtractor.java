package net.doepner.text;

import static net.doepner.text.CharCondition.IS_NUMBER_PART;
import static net.doepner.text.CharCondition.IS_WORD_PART;

public class WordExtractor implements TextProvider {

	private final TextModel model;

	public WordExtractor(TextModel model) {
		this.model = model;
	}

    @Override
	public String getText() {
		final String word = findSequence(model, IS_WORD_PART);
		if (word.length() > 0) {
			return word;			
		} else {
			return findSequence(model, IS_NUMBER_PART);
		}
	}

	private String findSequence(final TextModel model, CharCondition cond) {
		final int start = getStart(model, cond);
		final int end = getEnd(model, cond);

		return model.getText().substring(start, end);
	}

	private static int getStart(TextModel model, CharCondition cond) {
        final String text = model.getText();

        int pos = model.getOffset();
        while (pos > 0 && cond.matches(text.charAt(pos - 1))) {
			pos--;
		}
		return pos;
	}

	private static int getEnd(TextModel model, CharCondition cond) {
        final String text = model.getText();

		int pos = model.getOffset();
		while (pos < text.length() && cond.matches(text.charAt(pos))) {
			pos++;
		}
		return pos;
	}
}
