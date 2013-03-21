package net.doepner.text;

import static net.doepner.text.CharCondition.IS_NUMBER_PART;
import static net.doepner.text.CharCondition.IS_WORD_PART;

public class WordExtractor implements TextProvider {

    private final TextProvider model;
    private final TextCoordinates coords;

    public WordExtractor(TextProvider model, TextCoordinates coords) {
        this.model = model;
        this.coords = coords;
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

        int pos = coords.getOffset();
        while (pos > 0 && cond.matches(text.charAt(pos - 1))) {
            pos--;
        }
        return pos;
    }

    private int getEnd(CharCondition cond) {
        final String text = model.getText();

        int pos = coords.getOffset();
        while (pos < text.length() && cond.matches(text.charAt(pos))) {
            pos++;
        }
        return pos;
    }
}
