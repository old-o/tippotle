package net.doepner.typepad;

import net.doepner.lang.CanadianDeutsch;

/**
 * Main class with main method (entry point for app execution)
 */
public class Main {

    public static void main(String[] args) {
        new Application(new Context("Typepad", new CanadianDeutsch())).run();
    }
}
