package net.doepner.file;

import java.nio.file.Path;

import net.doepner.log.LogProvider;

/**
 * Helps with management of application files (paths)_
 */
public interface PathHelper extends LogProvider {

    Path findInDir(Path dir, String name, String... extension);

    Path findOrCreate(String name, PathCreator type);

}
