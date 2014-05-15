package net.doepner.app.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.LogProvider;

import java.nio.file.Path;

/**
 * Application context interface
 */
public interface IContext extends LogProvider {

    String getAppName();

    LanguageChanger getLanguageChanger();

    Path getHomeDirectory();

    String getEmailConfigFileName();
}
