package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileHelper implements IFileHelper {

    private static final String USER_HOME = System.getProperty("user.home");

    private final Path appDir;

    public FileHelper(String appName) {
        appDir = Paths.get(USER_HOME, "." + appName);
    }

    @Override
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

    @Override
    public Path getAppSubDirPath(String dirName)
            throws IOException {
        final Path dir = resolveAppPath(dirName);
        createDirIfNecessary(dir);
        return dir;
    }


    @Override
    public Path getAppFilePath(String fileName)
            throws IOException {
        final Path path = resolveAppPath(fileName);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path;
    }

    @Override
    public Path resolveFile(Path dir, String baseName, String extension)
            throws IOException {
        createDirIfNecessary(dir);
        return dir.resolve(baseName + '.' + extension);
    }

    @Override
    public Path resolveAppPath(String name)
            throws IOException {
        createDirIfNecessary(appDir);
        return appDir.resolve(name);
    }

    private void createDirIfNecessary(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }
    }
}
