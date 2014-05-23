package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.file.PathHelper;
import net.doepner.file.PathType;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Finds a resource on the file system
 */
public class FileFinder implements ResourceStore {

    private final PathHelper helper;
    private final Log log;

    public FileFinder(PathHelper helper,
                      LogProvider logProvider) {
        this.helper = helper;
        log = logProvider.getLog(getClass());
    }

    @Override
    public URL find(String name, MediaType mediaType, Language language,
                    String category) {
        final Path dir = getStorageDir(mediaType, language, category);
        return getUrl(dir, name, mediaType);
    }

    @Override
    public Path getStorageDir(MediaType mediaType, Language language,
                              String category) {
        final Path mediaDir = getDirectory(mediaType);
        final Path languageDir = mediaDir.resolve(toDirName(language));
        return languageDir.resolve(toDirName(category));
    }

    private static String toDirName(String category) {
        return category != null ? category : "_";
    }

    private static String toDirName(Language language) {
        return language != null ? language.getCode() : "_";
    }

    private Path getDirectory(MediaType mediaType) {
        final String dirName = mediaType.getGroupingName();
        return helper.findOrCreate(dirName, PathType.DIRECTORY);
    }

    private URL getUrl(Path dir, String name, MediaType mediaType) {
        final Path file = helper.findInDir(dir, name, mediaType);
        if (file != null) {
            try {
                return file.toUri().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
