package net.doepner.typepad;

import net.doepner.lang.LanguageChanger;

/**
 * Application context interface
 */
public interface IContext {

    String getAppName();

    LanguageChanger getLanguageChanger();
}
