package net.doepner.text;

/**
 * Create TextSpan instances
 */
public interface TextSpanFactory {

    TextSpan extractFrom(String fullText, int start, int end);

}
