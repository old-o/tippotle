package net.doepner.ui.text;

import net.doepner.text.CharCondition;
import net.doepner.ui.CharStyler;

import javax.swing.text.AttributeSet;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static net.doepner.text.CharConditions.DIGIT;
import static net.doepner.text.CharConditions.LETTER;
import static net.doepner.text.LetterTypes.ASPIRATED_PLOSIVE;
import static net.doepner.text.LetterTypes.FRICATIVE;
import static net.doepner.text.LetterTypes.NASAL;
import static net.doepner.text.LetterTypes.VOICED_PLOSIVE;
import static net.doepner.text.LetterTypes.VOWEL;

public final class AlphaNumStyler implements CharStyler {

    private static final AttributeSet DEFAULT = StyleAttributes.forColor(Color.DARK_GRAY);

    private final Map<CharCondition, AttributeSet> styles =
            new LinkedHashMap<>();

    public AlphaNumStyler() {
        style(DIGIT, Color.BLUE);
        style(VOWEL, Color.RED);
        style(ASPIRATED_PLOSIVE, Color.MAGENTA);
        style(VOICED_PLOSIVE, Color.PINK);
        style(FRICATIVE, Color.ORANGE);
        style(NASAL, Color.CYAN);
        style(LETTER, Color.DARK_GRAY);
    }

    private void style(CharCondition digit, Color color) {
        styles.put(digit, StyleAttributes.forColor(color));
    }

    @Override
    public AttributeSet getAttribs(char c) {
        for (Entry<CharCondition, AttributeSet> e : styles.entrySet()) {
            if (e.getKey().matches(c)) {
                return e.getValue();
            }
        }
        // otherwise
        return DEFAULT;
    }

}
