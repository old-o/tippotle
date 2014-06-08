package net.doepner.text;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import static net.doepner.log.Log.Level.info;
import static net.doepner.text.CharConditions.IS_NUMBER_PART;
import static net.doepner.text.CharConditions.IS_WORD_PART;

public class WordExtractor implements WordProvider {

    private final Log log;
    private final TextProvider textProvider;

    public WordExtractor(LogProvider logProvider, TextProvider textProvider) {
        log = logProvider.getLog(getClass());
        this.textProvider = textProvider;
    }

    @Override
    public String getWord(Integer position) {
        if (position == null) {
            return null;
        }
        final String word = findSequence(IS_WORD_PART, position);
        final String result = word.length() > 0 ? word : findSequence(IS_NUMBER_PART, position);
        log.as(info, "Word '{}' at position {}", word, position);
        return result;
    }

    @Override
    public Character getCharacter(Integer position) {
        if (position == null) {
            return null;
        }
        final String text = textProvider.getText();
        final char ch = text.length() > position ? text.charAt(position) : ' ';
        log.as(info, "Character '{}' at position {}", ch, position);
        return ch;
    }

    private String findSequence(CharCondition cond, int position) {
        final int start = getStart(cond, position);
        final int end = getEnd(cond, position);

        return textProvider.getText().substring(start, end);
    }

    private int getStart(CharCondition cond, int position) {
        final String text = textProvider.getText();

        int pos = position;
        while (pos > 0 && cond.matches(text.charAt(pos - 1))) {
            pos--;
        }
        return pos;
    }

    private int getEnd(CharCondition cond, int position) {
        final String text = textProvider.getText();

        int pos = position;
        while (pos < text.length() && cond.matches(text.charAt(pos))) {
            pos++;
        }
        return pos;
    }
}
