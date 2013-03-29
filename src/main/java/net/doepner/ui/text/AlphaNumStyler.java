package net.doepner.ui.text;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.doepner.text.CharCondition;

import static net.doepner.speech.LetterTypes.ASPIRATED_PLOSIVE;
import static net.doepner.speech.LetterTypes.FRICATIVE;
import static net.doepner.speech.LetterTypes.NASAL;
import static net.doepner.speech.LetterTypes.VOICED_PLOSIVE;
import static net.doepner.speech.LetterTypes.VOWEL;
import static net.doepner.text.CharConditions.DIGIT;
import static net.doepner.text.CharConditions.LETTER;

public class AlphaNumStyler implements CharStyler {

    private static final AttributeSet DEFAULT = attribs(Color.DARK_GRAY);

    private final Map<CharCondition, AttributeSet> styles =
            new LinkedHashMap<>();

    public AlphaNumStyler() {
        style(DIGIT, Color.BLUE);
        style(VOWEL, Color.RED);
        style(ASPIRATED_PLOSIVE, Color.MAGENTA);
        style(VOICED_PLOSIVE, Color.PINK);
        style(FRICATIVE, Color.ORANGE);
        style(NASAL, Color.CYAN);
        style(LETTER, Color.BLACK);
    }

    private void style(CharCondition digit, Color color) {
        styles.put(digit, attribs(color));
    }

    @Override
    public AttributeSet getAttribs(char c) {
        for (CharCondition condition : styles.keySet()) {
            if (condition.matches(c)) {
                return styles.get(condition);
            }
        }
        // otherwise
        return DEFAULT;
    }

    private static AttributeSet attribs(Color color) {
        final MutableAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setForeground(attribs, color);
        return attribs;
    }
}
