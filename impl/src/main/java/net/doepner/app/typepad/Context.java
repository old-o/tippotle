package net.doepner.app.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Application context
 */
public class Context implements IContext {

    private final Path homeDir = Paths.get(System.getProperty("user.home"));

    private final String appName;
    private final LanguageChanger languageChanger;
    private final LogProvider logProvider;
    private final String emailConfigFileName;

    public Context(String appName,
                   LanguageChanger languageChanger,
                   LogProvider logProvider,
                   String emailConfigFileName) {
        this.appName = appName;
        this.languageChanger = languageChanger;
        this.logProvider = logProvider;
        this.emailConfigFileName = emailConfigFileName;
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
    public Path getHomeDirectory() {
        return homeDir;
    }

    @Override
    public Log getLog(Class<?> clazz) {
        return logProvider.getLog(clazz);
    }

    @Override
    public String getEmailConfigFileName() {
        return emailConfigFileName;
    }

}
