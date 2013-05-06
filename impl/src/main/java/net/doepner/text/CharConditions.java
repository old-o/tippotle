package net.doepner.text;

/**
 * Character condition Constants and util methods
 */
public class CharConditions {

    public static CharCondition in(final String chars) {
        return new CharCondition() {
            @Override
            public final boolean matches(char c) {
                return chars.indexOf(Character.toLowerCase(c)) != -1;
            }
        };
    }

    public static CharCondition atLeastOneOf(final CharCondition cond1,
                                             final CharCondition cond2) {
        return new CharCondition() {
            @Override
            public boolean matches(char c) {
                return cond1.matches(c) || cond2.matches(c);
            }
        };
    }

    public static final CharCondition LETTER = new CharCondition() {
        @Override
        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    };

    public static final CharCondition DIGIT = new CharCondition() {
        @Override
        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    };

    public static final CharCondition IS_WORD_PART =
        atLeastOneOf(LETTER, in("-'"));

    public static final CharCondition IS_NUMBER_PART =
        atLeastOneOf(DIGIT, in(".,-+"));
}
