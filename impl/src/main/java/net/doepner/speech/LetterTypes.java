package net.doepner.speech;

import net.doepner.text.CharCondition;
import net.doepner.text.CharConditions;

/**
 * See http://en.wikipedia.org/wiki/International_Phonetic_Alphabet#Consonants
 */
public class LetterTypes {

    public static final CharCondition VOWEL = CharConditions.in("aeiouüöäy");

    public static final CharCondition ASPIRATED_PLOSIVE =
        CharConditions.in("ptk");

    public static final CharCondition VOICED_PLOSIVE =
        CharConditions.in("bdg");

    public static final CharCondition FRICATIVE =
        CharConditions.in("wsfhzxvß");

    public static final CharCondition NASAL = CharConditions.in("mn");
}
