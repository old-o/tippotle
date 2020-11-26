package org.oldo.resources;


import org.guppy4j.io.MediaType;
import org.oldo.lang.Language;

import java.net.URL;

/**
 * Finds resources, like images or audio files
 */
@FunctionalInterface
public interface ResourceFinder {

    URL find(MediaType  mediaType, String name, Language language, String category);

}
