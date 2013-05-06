package net.doepner.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.speech.Speaker;

/**
 * Application context
 */
public class Context implements IContext {

    private final LanguageChanger languageChanger;
    private final Speaker speaker;

    private final String appName;

    public Context(String appName,
                   LanguageChanger languageChanger,
                   Speaker speaker) {
        this.languageChanger = languageChanger;
        this.speaker = speaker;
        this.appName = appName;
    }

    @Override
    public LanguageChanger getLanguageChanger() {
        return languageChanger;
    }

    @Override
    public Speaker getSpeaker() {
        return speaker;
    }

    @Override
    public String getAppName() {
        return appName;
    }
}
