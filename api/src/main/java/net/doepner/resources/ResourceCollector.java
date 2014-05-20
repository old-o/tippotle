package net.doepner.resources;

import net.doepner.file.MediaType;

import java.net.URL;
import java.util.List;

/**
 * Finds resources in bulk
 */
public interface ResourceCollector {

    Iterable<URL> findAll(MediaType mediaType, String rawName, List<?> categories);

}
