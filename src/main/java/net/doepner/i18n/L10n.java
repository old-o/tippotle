package net.doepner.i18n;

import net.doepner.lang.Language;

/**
 * Localization provider
 */
public interface L10n<T> {

    String get(Language language, T t);

}
