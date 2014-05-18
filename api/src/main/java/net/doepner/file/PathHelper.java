package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Helps with management of application files (paths)_
 */
public interface PathHelper {

    Path findInDir(Path dir, String name, MediaType mediaType);

    Path findOrCreate(String name, PathCreator type);

    File writeFile(String name, Path targetDir, InputStream inputStream, FileType fileType) throws IOException;
}
