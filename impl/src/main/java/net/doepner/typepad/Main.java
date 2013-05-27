package net.doepner.typepad;

import java.io.IOException;

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
        final Speaker speaker = getSpeaker(languageChanger);

        final Context context = new Context("Typepad", languageChanger,
                speaker);

        new Application(context).run();
    }

    private static Speaker getSpeaker(LanguageChanger languageChanger) {
        try {
            return new ESpeaker(languageChanger);

        } catch (IOException e) {
            // use a dummy "speaker" that just writes to stdout
            return new Speaker() {
                @Override
                public void speak(String text) {
                    System.out.println("Speak: " + text);
                }
            };
        }
    }
}
