package net.doepner.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.speech.Speaker;

/**
 * Application context interface
 */
public interface IContext {

    String getAppName();

    Log getLog();

    Speaker getSpeaker();

    LanguageChanger getLanguageChanger();
}
