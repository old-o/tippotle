package net.doepner.i18n;

import net.doepner.lang.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * Localization map for objects of certain type
 */
public class L10nMapper<K, V> implements L10nRegistry<K, V> {

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
            map.put(language, new HashMap<K, V>());
        }
        return map.get(language);
    }
}
