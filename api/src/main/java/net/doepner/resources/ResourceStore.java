package net.doepner.resources;

import net.doepner.lang.Language;
import org.guppy4j.io.MediaType;

import java.nio.file.Path;

/**
 * A path based resource finder
 */
public interface ResourceStore extends ResourceFinder {

    Path storageDir(MediaType mediaType, Language language, String category);
}
