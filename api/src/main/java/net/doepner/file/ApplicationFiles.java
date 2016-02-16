package net.doepner.file;

import org.guppy4j.io.FileType;
import org.guppy4j.io.MediaType;
import org.guppy4j.io.PathCreator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Helps with management of application files (paths)_
 */
public interface ApplicationFiles {

    Path findInDir(Path dir, String name, MediaType mediaType);

    Path findOrCreate(String name, PathCreator type);

    Path writeFile(String name, Path targetDir, InputStream stream, FileType fileType)
            throws IOException;

}
