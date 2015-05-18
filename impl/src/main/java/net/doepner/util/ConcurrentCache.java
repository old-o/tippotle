package net.doepner.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Generic thread-safe cache
 */
public final class ConcurrentCache<K, V> implements Function<K, V> {

    private final Function<K, V> source;
    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();

    public ConcurrentCache(Function<K, V> source) {
        this.source = source;
    }

    @Override
    public V apply(K k) {
        final V cachedValue = map.get(k);
        if (cachedValue != null) {
            return cachedValue;
        } else {
            final V v = source.apply(k);
            map.put(k, v);
            return v;
        }
    }
}
