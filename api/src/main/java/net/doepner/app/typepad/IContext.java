package net.doepner.app.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.LogProvider;

/**
 * Application context interface
 */
public interface IContext extends LogProvider {

    String getAppName();

    LanguageChanger getLanguageChanger();
}
