package org.oldo.i18n;

import org.oldo.lang.Language;

/**
 * Registers localization mappings
 */
public interface L10nRegistry<K, V> extends L10n<K, V> {

    void put(Language language, K key, V value);

}
