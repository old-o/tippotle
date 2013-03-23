package net.doepner.text;

import static net.doepner.text.CharCondition.IS_NUMBER_PART;
import static net.doepner.text.CharCondition.IS_WORD_PART;

public class WordExtractor implements WordProvider {

    private final TextProvider model;

    public WordExtractor(TextProvider model) {
        this.model = model;
    }

    @Override
    public String getText(int position) {
        final String word = findSequence(IS_WORD_PART, position);
        if (word.length() > 0) {
            return word;
        } else {
            return findSequence(IS_NUMBER_PART, position);
        }
    }

    private String findSequence(CharCondition cond, int position) {
        final int start = getStart(cond, position);
        final int end = getEnd(cond, position);

        return model.getText().substring(start, end);
    }

    private int getStart(CharCondition cond, int position) {
        final String text = model.getText();

        int pos = position;
        while (pos > 0 && cond.matches(text.charAt(pos - 1))) {
            pos--;
        }
        return pos;
    }

    private int getEnd(CharCondition cond, int position) {
        final String text = model.getText();

        int pos = position;
        while (pos < text.length() && cond.matches(text.charAt(pos))) {
            pos++;
        }
        return pos;
    }
}
