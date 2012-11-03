package net.doepner.text;

import static net.doepner.text.CharCondition.IS_NUMBER_PART;
import static net.doepner.text.CharCondition.IS_WORD_PART;

public class WordExtractor implements TextProvider {

	private final TextProvider textProvider;
	private final TextCoordinates textCoords;

	public WordExtractor(TextProvider textProvider,
                         TextCoordinates textCoords) {
		this.textProvider = textProvider;
		this.textCoords = textCoords;
	}

	@Override
	public String getText() {
		final String text = textProvider.getText();
		final int offset = textCoords.getOffset();

		final String word = findSequence(text, offset, IS_WORD_PART);
		if (word.length() > 0) {
			return word;			
		} else {
			return findSequence(text, offset, IS_NUMBER_PART);
		}
	}

	private String findSequence(final String text, final int offset,
			CharCondition cond) {

		final int start = getStart(text, offset, cond);
		final int end = getEnd(text, offset, cond);

		return text.substring(start, end);
	}

	private static int getStart(String text, int offset, CharCondition cond) {
		int start = offset;
		while (start > 0 && cond.matches(text.charAt(start - 1))) {
			start--;
		}
		return start;
	}

	private static int getEnd(String text, int offset, CharCondition cond) {
		int end = offset;
		while (end < text.length() && cond.matches(text.charAt(end))) {
			end++;
		}
		return end;
	}
}
