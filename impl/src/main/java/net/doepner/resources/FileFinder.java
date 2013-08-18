package net.doepner.resources;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;

/**
 * Finds a resource on the file system
 */
public class FileFinder implements ResourceFinder {

    private final PathHelper pathHelper;
    private final Path baseDir;
    private final LanguageProvider languageProvider;
    private final String[] extensions;

    public FileFinder(PathHelper pathHelper, LanguageProvider languageProvider,
                      Path baseDir, String... extensions) {
        this.pathHelper = pathHelper;
        this.languageProvider = languageProvider;
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
