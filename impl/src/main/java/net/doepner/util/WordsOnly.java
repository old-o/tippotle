package net.doepner.util;

import java.util.regex.Pattern;

/**
 * Collapses whitespace into single space and converts
 * non-alphanumeric characters to underscores
 * <p>
 * TODO: Support non-ASCII alphanumeric characters ?
 */
public final class WordsOnly implements StringNormalizer {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern NON_WORD_CHARS = Pattern.compile("\\W");

    @Override
    public String normalize(String rawName) {
        return replace(replace(rawName.toLowerCase(), WHITESPACE, " "), NON_WORD_CHARS, "_");
    }

    private static String replace(CharSequence text, Pattern pattern, String replacement) {
        return pattern.matcher(text).replaceAll(replacement);
    }
}
