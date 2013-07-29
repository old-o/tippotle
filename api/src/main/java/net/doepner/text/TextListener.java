package net.doepner.text;

/**
 * Listens for new or updated text
 */
public interface TextListener {

    void handleText(String text);
}
