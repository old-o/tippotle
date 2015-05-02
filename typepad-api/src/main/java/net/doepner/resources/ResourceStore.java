package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.nio.file.Path;

/**
 * A path based resource finder
 */
public interface ResourceStore extends ResourceFinder {

    Path storageDir(MediaType mediaType, Language language, String category);
}
