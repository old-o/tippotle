package net.doepner.i18n;

import java.util.HashMap;
import java.util.Map;

import net.doepner.lang.ILanguage;

/**
 * Localization map for objects of certain type
 */
public class L10nMapper<K, V> implements L10n<K, V>, L10nRegistry<K, V> {

    private final Map<ILanguage, Map<K, V>> map = new HashMap<>();

    @Override
    public void put(ILanguage language, K key, V value) {
        getMap(language).put(key, value);
    }

    @Override
    public V get(K key, ILanguage language) {
        return getMap(language).get(key);
    }

    private Map<K, V> getMap(ILanguage language) {
        if (!map.containsKey(language)) {
            map.put(language, new HashMap<K, V>());
        }
        return map.get(language);
    }
}
