package net.doepner.i18n;

import net.doepner.lang.Language;

/**
 * Localization provider
 */
public interface L10n<K, V> {

    V get(K k, Language language);

}
