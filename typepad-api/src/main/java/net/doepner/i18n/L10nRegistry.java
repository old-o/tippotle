package net.doepner.i18n;

import net.doepner.lang.Language;

/**
 * Registers localization mappings
 */
public interface L10nRegistry<K, V> extends L10n<K, V> {

    void put(Language language, K key, V value);

}
