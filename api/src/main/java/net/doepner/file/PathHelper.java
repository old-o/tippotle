package net.doepner.file;

import java.nio.file.Path;

/**
 * Helps with management of application files (paths)_
 */
public interface PathHelper {

    Path findInDir(Path dir, String name, MediaType mediaType);

    Path findOrCreate(String name, PathCreator type);

}
