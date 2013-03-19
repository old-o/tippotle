package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper  {

    private final Path appDir;

    public FileHelper(Path appDir) {
        this.appDir = appDir;
    }

    public File findFile(Path dir, String name, String extension) {
        final Path path;
        try {
            path = resolveFile(dir, name, extension);
        } catch (IOException e) {
            return null;
        }
        if (Files.exists(path)) {
            return path.toFile();
        } else {
            return null;
        }
    }

    public Path getAppDirPath(String name)
            throws IOException {
        final Path path = resolveFile(name);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path;
    }

    public Path resolveFile(Path dir, String baseName, String extension)
            throws IOException {
        ensureExists(dir);
        return dir.resolve(baseName + '.' + extension);
    }

    public Path resolveFile(String name)
            throws IOException {
        ensureExists(appDir);
        return appDir.resolve(name);
    }

    private void ensureExists(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
    }
}
