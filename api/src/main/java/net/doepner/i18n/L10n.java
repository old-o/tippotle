package net.doepner.i18n;

import net.doepner.lang.ILanguage;

/**
 * Localization provider
 */
public interface L10n<K, V> {

    V get(K k, ILanguage language);

}
