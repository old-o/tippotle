package org.oldo.text;

/**
 * Create TextSpan instances
 */
public interface TextSpanFactory {

    TextSpan extractFrom(String fullText, int start, int end);

}
