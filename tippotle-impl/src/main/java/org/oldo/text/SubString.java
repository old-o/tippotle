package org.oldo.text;

/**
 * A simple text span
 */
public final class SubString implements TextSpan {

    private final String content;
    private final int start;

    public SubString(String content, int start) {
        this.content = content;
        this.start = start;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return start + content.length();
    }

    @Override
    public String getContent() {
        return content;
    }
}
