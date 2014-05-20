package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.net.URL;

/**
 * Finds resources, like images or audio files
 */
public interface ResourceFinder {

    URL find(String name, MediaType mediaType, Language language, String category);

}
