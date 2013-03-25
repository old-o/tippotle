package net.doepner.typepad;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.doepner.text.CharCondition;
import net.doepner.ui.text.CharStyler;

import static net.doepner.text.CharCondition.DIGIT;
import static net.doepner.text.CharCondition.LETTER;
import static net.doepner.typepad.CharType.ASPIRATED_PLOSIVE;
import static net.doepner.typepad.CharType.VOICED_PLOSIVE;
import static net.doepner.typepad.CharType.VOWEL;

public class AlphaNumStyler implements CharStyler {

    private static final AttributeSet DEFAULT = attribs(Color.DARK_GRAY);

    private final Map<CharCondition, AttributeSet> styles = new HashMap<>();

    public AlphaNumStyler() {
        style(DIGIT, Color.BLUE);
        style(VOWEL, Color.ORANGE);
        style(ASPIRATED_PLOSIVE, Color.RED);
        style(VOICED_PLOSIVE, Color.PINK);
        style(LETTER, Color.MAGENTA);
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
