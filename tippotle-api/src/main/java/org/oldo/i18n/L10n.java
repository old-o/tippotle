package org.oldo.i18n;

import org.oldo.lang.Language;

/**
 * Localization provider
 */
public interface L10n<K, V> {

    V get(K k, Language language);

}
