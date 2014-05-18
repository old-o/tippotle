package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.nio.file.Path;

/**
 * TODO: Document this!
 */
public interface ResourceStore extends ResourceFinder {

    Path getStorageDir(MediaType mediaType, Language language, String category);
}
