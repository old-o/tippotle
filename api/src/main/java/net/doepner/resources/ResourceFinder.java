package net.doepner.resources;


import net.doepner.lang.Language;
import org.guppy4j.io.MediaType;

import java.net.URL;

/**
 * Finds resources, like images or audio files
 */
@FunctionalInterface
public interface ResourceFinder {

    URL find(MediaType  mediaType, String name, Language language, String category);

}
