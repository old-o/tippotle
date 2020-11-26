package org.oldo.i18n;

import org.oldo.lang.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * Localization map for objects of certain type
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public final class L10nMapper<K, V> implements L10nRegistry<K, V> {

    private final Map<Language, Map<K, V>> map = new HashMap<>();

    @Override
    public void put(Language language, K key, V value) {
        getMap(language).put(key, value);
    }

    @Override
    public V get(K key, Language language) {
        return getMap(language).get(key);
    }

    private Map<K, V> getMap(Language language) {
        if (!map.containsKey(language)) {
            map.put(language, new HashMap<>());
        }
        return map.get(language);
    }
}
