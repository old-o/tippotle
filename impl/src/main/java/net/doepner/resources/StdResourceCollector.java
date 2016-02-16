package net.doepner.resources;

import org.guppy4j.io.MediaType;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Standard implementation of ResourceCollector,
 * based on a wrapped ResourceFinder
 */
public final class StdResourceCollector implements ResourceCollector {

    private final ResourceFinder finder;

    public StdResourceCollector(ResourceFinder finder) {
        this.finder = finder;
    }

    @Override
    public Iterable<URL> findAll(MediaType mediaType, String name, List<?> categories) {
        final Collection<URL> list = new LinkedList<>();
        for (final Object category : categories) {
            list.add(finder.find(mediaType, name, null, category.toString()));
        }
        return list;
    }
}
