package org.oldo.text;

import static java.lang.Character.toLowerCase;

/**
 * Character condition Constants and util methods
 */
public final class CharConditions {

    private CharConditions() {
        // no instance
    }

    public static CharCondition in(final String chars) {
        return c -> chars.indexOf(toLowerCase(c)) != -1;
    }

    public static CharCondition atLeastOneOf(final CharCondition cond1,
                                             final CharCondition cond2) {
        return c -> cond1.matches(c) || cond2.matches(c);
    }

    public static final CharCondition LETTER = Character::isLetter;
    public static final CharCondition DIGIT = Character::isDigit;

    public static final CharCondition IS_WORD_PART =
            atLeastOneOf(LETTER, in("-'"));

    public static final CharCondition IS_NUMBER_PART =
            atLeastOneOf(DIGIT, in(".,-+"));
}
