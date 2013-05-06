package net.doepner.typepad;

import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;

/**
 * Main class with main method (entry point for app execution)
 */
public class Main {

    public static void main(String[] args) {

        final LanguageChanger languageChanger = new EnglishOrGerman();
        final Speaker speaker = new ESpeaker(languageChanger);

        final Context context = new Context("Typepad",
            languageChanger, speaker);

        new Application(context).run();
    }
}
