package net.doepner.resources;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Finds a resource on the file system
 */
public class FileFinder implements ResourceFinder {

    private final LanguageProvider languageProvider;
    private final PathHelper pathHelper;
    private final String[] extensions;
    private final Path baseDir;

    public FileFinder(PathHelper pathHelper, LanguageProvider languageProvider,
                      Path baseDir, String... extensions) {
        this.languageProvider = languageProvider;
        this.pathHelper = pathHelper;
        this.extensions = extensions;
        this.baseDir = baseDir;
    }

    @Override
    public URL find(String name) {
        final Path dir = baseDir.resolve(languageProvider.getLanguage().getCode());
        final Path path = pathHelper.findInDir(dir, name, extensions);
        if (path == null) {
            return null;
        } else {
            try {
                return path.toUri().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
