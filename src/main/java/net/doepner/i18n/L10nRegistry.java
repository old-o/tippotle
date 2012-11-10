package net.doepner.i18n;

import net.doepner.lang.Language;

/**
 * Registers localization mappings
 */
public interface L10nRegistry<T> {

    void put(Language language, T t, String s);

}
