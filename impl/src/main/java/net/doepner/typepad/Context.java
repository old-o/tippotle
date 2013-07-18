package net.doepner.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.speech.Speaker;

/**
 * Application context
 */
public class Context implements IContext {

    private final String appName;
    private final Log log;

    private final LanguageChanger languageChanger;
    private final Speaker speaker;

    public Context(String appName, Log log,
                   LanguageChanger languageChanger,
                   Speaker speaker) {
        this.appName = appName;
        this.log = log;
        this.languageChanger = languageChanger;
        this.speaker = speaker;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public Log getLog() {
        return log;
    }

    @Override
    public LanguageChanger getLanguageChanger() {
        return languageChanger;
    }

    @Override
    public Speaker getSpeaker() {
        return speaker;
    }
}
