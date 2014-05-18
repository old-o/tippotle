package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.file.PathHelper;
import net.doepner.file.PathType;
import net.doepner.lang.Language;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Finds a resource on the file system
 */
public class FileFinder implements ResourceStore {

    private final PathHelper helper;

    public FileFinder(PathHelper helper) {
        this.helper = helper;
    }

    @Override
    public URL find(MediaType mediaType, Language language,
                    String category, String name) {
        final Path dir = getStorageDir(mediaType, language, category);
        return getUrl(dir, name, mediaType);
    }

    @Override
    public Path getStorageDir(MediaType mediaType, Language language, String category) {
        final Path mediaDir = getDirectory(mediaType);
        final Path languageDir = mediaDir.resolve(toDirName(language));
        return languageDir.resolve(toDirName(category));
    }

    private Path getDirectory(MediaType mediaType) {
        final String dirName = mediaType.getGroupingName();
        return helper.findOrCreate(dirName, PathType.DIRECTORY);
    }

    private String toDirName(String category) {
        return category != null ? category : UNSPECIFIED_PATH_PART;
    }

    private String toDirName(Language language) {
        return language != null ? language.getCode() : UNSPECIFIED_PATH_PART;
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
