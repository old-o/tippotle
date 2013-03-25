package net.doepner.typepad;

import net.doepner.text.CharCondition;

public enum CharType implements CharCondition {

    VOWEL("aeiouüöäy"),
    ASPIRATED_PLOSIVE("ptk"),
    VOICED_PLOSIVE("bdg");

    private final String chars;

    private CharType(final String chars) {
        this.chars = chars;
    }

    @Override
    public final boolean matches(char c) {
        return chars.indexOf(Character.toLowerCase(c)) != -1;
    }
}
