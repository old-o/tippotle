package net.doepner.typepad;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.doepner.ui.text.CharStyler;

public class AlphaNumStyler implements CharStyler {

    private static final Color LIGHT_GREEN = new Color(144, 238, 144);
	
	private static final AttributeSet DIGIT = attribs(Color.GREEN);
	private static final AttributeSet LETTER = attribs(Color.BLUE);
	private static final AttributeSet VOWEL = attribs(Color.RED);
	private static final AttributeSet OTHER = attribs(Color.GRAY);
    private static final AttributeSet ASPIRATED_PLOSIVE = attribs(Color.GREEN);
    private static final AttributeSet VOICED_PLOSIVE = attribs(LIGHT_GREEN);

	@Override
	public AttributeSet getAttribs(char c) {
		if (Character.isDigit(c)) {
			return DIGIT;
		}
		if (isVowel(c)) {
			return VOWEL;
		}
        if (isAspiratedPlosive(c)) {
            return ASPIRATED_PLOSIVE;
        }
        if (isVoicedPlosive(c)) {
            return VOICED_PLOSIVE;
        }
        if (Character.isLetter(c)) {
			return LETTER;
		}
		return OTHER;
	}

    private static boolean isAspiratedPlosive(char c) {
        return "PTK".indexOf(Character.toUpperCase(c)) != -1;
    }

    private static boolean isVoicedPlosive(char c) {
        return "BDG".indexOf(Character.toUpperCase(c)) != -1;
    }

    private static boolean isVowel(char c) {
        return "AEIOUÜÖÄY".indexOf(Character.toUpperCase(c)) != -1;
    }

    private static AttributeSet attribs(Color color) {
		final MutableAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setForeground(attribs, color);
		return attribs;
	}
}
