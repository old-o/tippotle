package net.doepner.app.api;

import net.doepner.lang.LanguageChanger;
import net.doepner.speech.Speaker;

/**
 * Application context interface
 */
public interface IContext {

    Speaker getSpeaker();

    LanguageChanger getLanguageChanger();

    String getAppName();
}
