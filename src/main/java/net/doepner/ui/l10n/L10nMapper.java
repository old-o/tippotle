package net.doepner.ui.l10n;

import java.util.HashMap;
import java.util.Map;

import net.doepner.lang.Language;

/**
 * Localization map for objects of certain type
 */
public class L10nMapper<T> implements L10n<T> {

    private final Map<Language, Map<T, String>> map = new HashMap<>();

    public void put(Language language, T t, String s) {
        getMap(language).put(t, s);
    }

    @Override
    public String get(Language language, T t) {
        return getMap(language).get(t);
    }

    private Map<T, String> getMap(Language language) {
        final Map<T, String> m = map.get(language);
        if (m != null) {
            return m;
        } else {
            final Map<T, String> mNew = new HashMap<>();
            map.put(language, mNew);
            return mNew;
        }
    }
}
