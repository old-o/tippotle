package net.doepner.speech;

import java.io.IOException;

import net.doepner.lang.LanguageProvider;


public class ESpeaker implements Speaker {

    private final LanguageProvider ctx;

    public ESpeaker(LanguageProvider ctx) throws IOException {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "espeak";
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
                "espeak", "-v", ctx.getLanguage().getCode(), text
        });
    }
}
