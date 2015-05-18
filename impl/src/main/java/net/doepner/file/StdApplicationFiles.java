package net.doepner.file;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.exists;
import static net.doepner.file.PathType.DIRECTORY;
import static org.guppy4j.log.Log.Level.debug;
import static org.guppy4j.log.Log.Level.info;

public final class StdApplicationFiles implements ApplicationFiles {

    private static final Pattern SPACES = Pattern.compile(" ", Pattern.LITERAL);

    private final Path appDir;
    private final Log log;

    public StdApplicationFiles(LogProvider logProvider,
                               String appName, Path homeDir) {
        log = logProvider.getLog(getClass());
        appDir = homeDir.resolve('.' + appName.toLowerCase());
        createIfNecessary(appDir, DIRECTORY);
    }

    @Override
    public Path findInDir(Path dir, String name, MediaType mediaType) {
        createIfNecessary(dir, DIRECTORY);
        for (FileType fileType : mediaType.fileTypes()) {
            final Path path = resolveIn(dir, name, fileType);
            if (exists(path)) {
                logFindResult(dir, name, mediaType, path);
                return path;
            }
        }
        logFindResult(dir, name, mediaType, null);
        return null;
    }

    private void logFindResult(Path dir, String name, MediaType mediaType, Path result) {
        final boolean resultFound = result == null;
        log.as(resultFound ? debug : info,
                "findInDir({}, {}, {}) == {}", dir, name, mediaType, result);
    }

    @Override
    public Path findOrCreate(String name, PathCreator type) {
        return createIfNecessary(appDir.resolve(name), type);
    }

    @Override
    public Path writeFile(String name, Path targetDir,
                          InputStream stream, FileType fileType) throws IOException {
        final Path path = resolveIn(targetDir, name, fileType);
        copy(stream, path);
        return path;
    }

    private static Path resolveIn(Path dir, CharSequence name, FileType fileType) {
        return dir.resolve(fileType.fileName(sanitize(name)));
    }


    private static String sanitize(CharSequence name) {
        return SPACES.matcher(name).replaceAll("_");
    }

    private static Path createIfNecessary(Path path, PathCreator creator) {
        if (exists(path)) {
            return path;
        }
        try {
            return creator.create(path);
        } catch (FileAlreadyExistsException e) {
            // must have been a race condition
            return path;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
