package net.doepner.resources;

import java.net.URL;

/**
 * Finds classpath resources
 */
public class ClasspathFinder implements ResourceFinder {

    private final String[] extensions;

    public ClasspathFinder(String... extensions) {
        this.extensions = extensions;
    }

    @Override
    public URL find(String name) {
        for (String extension : extensions) {
            final URL resource = getClass().getResource(name + extension);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }
}
