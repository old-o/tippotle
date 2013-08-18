package net.doepner.resources;

import java.net.URL;
import java.util.Arrays;

/**
 * Finds files in classpath, file system or online
 */
public class DelegatingResourceFinder implements ResourceFinder {

    private final Iterable<ResourceFinder> finders;

    public DelegatingResourceFinder(ResourceFinder... finders) {
        this.finders = Arrays.asList(finders);
    }

    public URL find(String name) {
        for (ResourceFinder finder : finders) {
            final URL resource = finder.find(name);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }
}
