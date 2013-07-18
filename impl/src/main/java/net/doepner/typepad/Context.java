package net.doepner.typepad;

import net.doepner.lang.LanguageChanger;

/**
 * Application context
 */
public class Context implements IContext {

    private final String appName;
    private final LanguageChanger languageChanger;

    public Context(String appName, LanguageChanger languageChanger) {
        this.appName = appName;
        this.languageChanger = languageChanger;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public LanguageChanger getLanguageChanger() {
        return languageChanger;
    }
}
