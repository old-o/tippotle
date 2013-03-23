package net.doepner.file;

import java.io.File;
import java.nio.file.Path;

/**
 * Helps with management of application files
 */
public interface IFileHelper {

    File findInDir(Path dir, String name, String extension);

    Path findOrCreate(String dirName, PathType type);
}
