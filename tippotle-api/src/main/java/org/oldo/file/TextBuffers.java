package org.oldo.file;

/**
 * Saves and loads String by index number from a persistent store
 */
public interface TextBuffers {

    /**
     * @param text Text to save
     * @param i    The index of the buffer to save the text in
     */
    void save(String text, int i);

    /**
     * @param i The index of the buffer to load the text from
     * @return The loaded text
     */
    String load(int i);

}
