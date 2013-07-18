package net.doepner.typepad;

import net.doepner.lang.EnglishOrGerman;

/**
 * Main class with main method (entry point for app execution)
 */
public class Main {

    public static void main(String[] args) {
        new Application(new Context("Typepad", new EnglishOrGerman())).run();
    }
}
