package net.doepner.speech;

import net.doepner.lang.LanguageProvider;

import java.io.IOException;


public class ESpeaker implements TestableSpeaker {

    private final LanguageProvider languageProvider;

    public ESpeaker(LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;
    }

    @Override
    public String getName() {
        return "robbi";
    }

    @Override
    public void speak(String text) {
        try {
            doSpeak(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void test() {
        try {
            Runtime.getRuntime().exec(new String[]{"espeak", "-h"});
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void doSpeak(String text) throws IOException {
        Runtime.getRuntime().exec(new String[]{
                "espeak", "-v", languageProvider.getLanguage().getCode(), text
        });
    }
}
