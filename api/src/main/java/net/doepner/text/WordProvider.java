package net.doepner.text;

/**
 * Determines the word at a specified text position
 */
public interface WordProvider {

    String getWord(int position);

    char getCharacter(int position);
}
