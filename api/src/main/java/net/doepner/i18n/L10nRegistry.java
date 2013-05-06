package net.doepner.i18n;

import net.doepner.lang.ILanguage;

/**
 * Registers localization mappings
 */
public interface L10nRegistry<K, V> {

    void put(ILanguage language, K key, V value);

}
