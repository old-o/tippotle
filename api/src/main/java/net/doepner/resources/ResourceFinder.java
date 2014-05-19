package net.doepner.resources;

import java.net.URL;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

/**
 * Finds resources, like images or audio files
 */
public interface ResourceFinder {

    URL find(MediaType mediaType, Language language,
             String category, String name);

    Iterable<URL> findAll(MediaType mediaType, String name);

    String UNSPECIFIED_PATH_PART = "_";
}
