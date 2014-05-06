package net.doepner.file;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static net.doepner.log.Log.Level.error;

public final class StdPathHelper implements PathHelper {

    private final Path appDir;
    private final Log log;

    public StdPathHelper(String appName, Path homeDir,
                         LogProvider logProvider) {
        log = logProvider.getLog(getClass());
        appDir = homeDir.resolve("." + appName.toLowerCase());
        createIfNecessary(appDir, PathType.DIRECTORY);
    }

    @Override
    public Path findInDir(Path dir, String name, String... extensions) {
        createIfNecessary(dir, PathType.DIRECTORY);
        for (String extension : extensions) {
            final Path path = dir.resolve(name + '.' + extension);
            if (Files.exists(path)) {
                return path;
            }
        }
        final String extList = Arrays.toString(extensions);
        log.$(error, "findInDir({}, {}, {}) == null", dir, name, extList);
        return null;
    }

    @Override
    public Path findOrCreate(String name, PathCreator type) {
        return createIfNecessary(appDir.resolve(name), type);
    }

    private static Path createIfNecessary(Path path, PathCreator creator) {
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
