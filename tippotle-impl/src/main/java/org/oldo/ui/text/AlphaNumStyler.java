package org.oldo.ui.text;

import org.oldo.text.CharCondition;
import org.oldo.ui.CharStyler;

import javax.swing.text.AttributeSet;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.oldo.text.CharConditions.DIGIT;
import static org.oldo.text.CharConditions.LETTER;
import static org.oldo.text.LetterTypes.ASPIRATED_PLOSIVE;
import static org.oldo.text.LetterTypes.FRICATIVE;
import static org.oldo.text.LetterTypes.NASAL;
import static org.oldo.text.LetterTypes.VOICED_PLOSIVE;
import static org.oldo.text.LetterTypes.VOWEL;

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
