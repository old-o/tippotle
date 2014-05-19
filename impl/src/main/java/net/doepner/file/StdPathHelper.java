package net.doepner.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import static net.doepner.log.Log.Level.info;

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
    public Path findInDir(Path dir, String name, MediaType mediaType) {
        createIfNecessary(dir, PathType.DIRECTORY);
        for (FileType fileType : mediaType.getFileTypes()) {
            final Path path = dir.resolve(sanitize(name) + '.' + fileType.getExtension());
            if (Files.exists(path)) {
                log.$(info, "findInDir({}, {}, {}) == {}", dir, name, mediaType, path);
                return path;
            }
        }
        log.$(info, "findInDir({}, {}, {}) == null", dir, name, mediaType);
        return null;
    }

    @Override
    public Path findOrCreate(String name, PathCreator type) {
        return createIfNecessary(appDir.resolve(name), type);
    }

    @Override
    public File writeFile(String name, Path targetDir,
                          InputStream stream, FileType fileType) throws IOException {

        final ReadableByteChannel rbc = Channels.newChannel(stream);
        final File file = targetDir.resolve(sanitize(name) + '.' + fileType.getExtension()).toFile();

        try (final FileOutputStream fos = new FileOutputStream(file)) {
            final FileChannel channel = fos.getChannel();
            channel.transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        return file;
    }

    private static String sanitize(String name) {
        return name.replace(" ", "_");
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
            throw new IllegalStateException(e);
        }
    }
}
