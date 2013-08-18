package net.doepner.speech;

import java.io.IOException;

import net.doepner.lang.LanguageProvider;


public class ESpeaker implements Speaker {

    private final LanguageProvider languageProvider;

    public ESpeaker(LanguageProvider languageProvider) throws IOException {
        this.languageProvider = languageProvider;
    }

    @Override
    public String getName() {
        return "robby";
    }

    @Override
    public void speak(String text) {
        try {
            doSpeak(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doSpeak(String text) throws IOException {
        Runtime.getRuntime().exec(new String[]{
            "espeak", "-v", languageProvider.getLanguage().getCode(), text
        });
    }
}
