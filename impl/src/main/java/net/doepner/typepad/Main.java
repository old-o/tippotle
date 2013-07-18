package net.doepner.typepad;

import java.io.IOException;

import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.log.StdLog;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;

/**
 * Main class with main method (entry point for app execution)
 */
public class Main {

    public static void main(String[] args) {
        final Log log = new StdLog();

        final LanguageChanger languageChanger = new EnglishOrGerman();
        final Speaker speaker = getSpeaker(languageChanger, log);

        final Context context = new Context("Typepad", log, languageChanger, speaker);

        new Application(context).run();
    }

    private static Speaker getSpeaker(LanguageChanger languageChanger,
                                      final Log log) {
        try {
            return new ESpeaker(languageChanger);

        } catch (IOException e) {
            log.error(e);
            log.info("We'll use a dummy 'speaker' that just logs the output");

            return new Speaker() {
                @Override
                public void speak(String text) {
                    log.info("Speak: " + text);
                }
            };
        }
    }
}
