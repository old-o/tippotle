package net.doepner.file;

import java.io.File;
import java.nio.file.Path;

/**
 * Helps with management of application files (paths)_
 */
public interface PathHelper {

    Path findInDir(Path dir, String name, String... extension);

    Path findOrCreate(String dirName, PathCreator type);
}
