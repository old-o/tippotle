package net.doepner.i18n;

import java.util.HashMap;
import java.util.Map;

import net.doepner.lang.Language;

/**
 * Localization map for objects of certain type
 */
public class L10nMapper<T> implements L10n<T>, L10nRegistry<T> {

    private final Map<Language, Map<T, String>> map = new HashMap<>();

    @Override
    public void put(Language language, T t, String s) {
        getMap(language).put(t, s);
    }

    @Override
    public String get(Language language, T t) {
        return getMap(language).get(t);
    }

    private Map<T, String> getMap(Language language) {
        if (!map.containsKey(language)) {
            map.put(language, new HashMap<T, String>());
        }
        return map.get(language);
    }
}
