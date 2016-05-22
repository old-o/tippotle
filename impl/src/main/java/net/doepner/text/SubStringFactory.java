package net.doepner.text;

/**
 * Extracts sub-strings
 */
public final class SubStringFactory implements TextSpanFactory {

    @Override
    public TextSpan extractFrom(String fullText, int start, int end) {
        return start < end && end <= fullText.length()
                ? new SubString(fullText.substring(start, end), start)
                : TextSpan.NONE;
    }
}
