package org.oldo.resources;

import org.guppy4j.io.MediaType;
import org.oldo.lang.Language;

import java.nio.file.Path;

/**
 * A path based resource finder
 */
public interface ResourceStore extends ResourceFinder {

    Path storageDir(MediaType mediaType, Language language, String category);
}
