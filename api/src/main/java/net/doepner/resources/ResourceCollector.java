package net.doepner.resources;

import org.guppy4j.io.MediaType;

import java.net.URL;
import java.util.List;

/**
 * Finds resources in bulk
 */
public interface ResourceCollector {

    Iterable<URL> findAll(MediaType mediaType, String rawName, List<?> categories);

}
