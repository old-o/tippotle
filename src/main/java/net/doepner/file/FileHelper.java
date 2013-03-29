package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.doepner.file.PathType.DIRECTORY;

public final class FileHelper implements IFileHelper {

    private static final String USER_HOME = System.getProperty("user.home");

    private final Path appDir;

    public FileHelper(String appName) {
        appDir = Paths.get(USER_HOME, "." + appName.toLowerCase());
    }

    @Override
    public File findInDir(Path dir, String name, String extension) {
        createIfNecessary(dir, DIRECTORY);
        final Path path = dir.resolve(name + '.' + extension);
        return Files.exists(path) ? path.toFile() : null;
    }

    @Override
    public Path findOrCreate(String name, PathType type) {
        return createIfNecessary(appDir.resolve(name), type);
    }

    private static Path createIfNecessary(Path path,PathCreator creator) {
        if (Files.exists(path)) {
            return path;
        }
        try {
            return creator.create(path);
        } catch (FileAlreadyExistsException e) {
            // must have been a race condition
            return path;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
