package org.oldo.text;

/**
 * Determines the word at a specified text position
 */
public interface WordProvider {

    TextSpan getWord(Integer position);

    Character getCharacter(Integer position);
}
