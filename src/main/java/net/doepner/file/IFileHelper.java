package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Helps with management of application files
 */
public interface IFileHelper {

    File findFile(Path dir, String name, String extension);

    Path getAppSubDirPath(String dirName) throws IOException;

    Path getAppFilePath(String fileName) throws IOException;

    Path resolveFile(Path dir, String baseName, String extension)
            throws IOException;

    Path resolveAppPath(String name) throws IOException;

}
