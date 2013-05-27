package net.doepner.speech;

import java.io.IOException;

import net.doepner.lang.Language;
import net.doepner.lang.LanguageProvider;


public class ESpeaker implements Speaker {

    private final LanguageProvider ctx;

    public ESpeaker(LanguageProvider ctx) throws IOException {
        this.ctx = ctx;
        doSpeak("test");
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
            "espeak", "-v", getVoice(text), text
        });
    }

    private String getVoice(String text) {
        if (isZed(text)) {
            return "en";
        }
        return ctx.getLanguage().getCode();
    }

    private boolean isZed(String text) {
        return Language.ENGLISH == ctx.getLanguage()
            && "z".equalsIgnoreCase(text);
    }

}
