package net.doepner.resources;

import net.doepner.file.MediaType;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Standard implementation of ResourceCollector,
 * based on a wrapped ResourceFinder
 */
public class StdResourceCollector implements ResourceCollector {

    private ResourceFinder finder;

    public StdResourceCollector(ResourceFinder finder) {
        this.finder = finder;
    }

    @Override
    public Iterable<URL> findAll(MediaType mediaType, String name, List<?> categories) {
        final List<URL> list = new LinkedList<>();
        for (Object category : categories) {
            list.add(finder.find(mediaType, name, null, category.toString()));
        }
        return list;
    }
}
