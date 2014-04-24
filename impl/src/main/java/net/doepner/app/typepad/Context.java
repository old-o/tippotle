package net.doepner.app.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

/**
 * Application context
 */
public class Context implements IContext {

    private final String appName;
    private final LanguageChanger languageChanger;
    private final LogProvider logProvider;

    public Context(String appName,
                   LanguageChanger languageChanger,
                   LogProvider logProvider) {
        this.appName = appName;
        this.languageChanger = languageChanger;
        this.logProvider = logProvider;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public LanguageChanger getLanguageChanger() {
        return languageChanger;
    }

    @Override
    public Log getLog(Class<?> clazz) {
        return logProvider.getLog(clazz);
    }
}
