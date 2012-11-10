package net.doepner.file;

/**
 * Saves and loads String by index number from a persistent store
 */
public interface TextBuffers {

    void save(String text, int i);

    String load(int i);
}
