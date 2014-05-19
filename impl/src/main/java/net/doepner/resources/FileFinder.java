package net.doepner.resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.doepner.file.MediaType;
import net.doepner.file.PathHelper;
import net.doepner.file.PathType;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import static net.doepner.log.Log.Level.error;

/**
 * Finds a resource on the file system
 */
public class FileFinder implements ResourceStore {

    private static final DirectoryStream.Filter<? super Path> DIRS_ONLY =
        new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isDirectory(entry);
            }
        };

    private final PathHelper helper;
    private final Log log;

    public FileFinder(PathHelper helper,
                      LogProvider logProvider) {
        this.helper = helper;
        log = logProvider.getLog(getClass());
    }

    @Override
    public URL find(MediaType mediaType, Language language,
                    String category, String name) {
        final Path dir = getStorageDir(mediaType, language, category);
        return getUrl(dir, name, mediaType);
    }

    @Override
    public Iterable<URL> findAll(MediaType mediaType, String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }

        final List<URL> results = new LinkedList<>();

        try (DirectoryStream<Path> ds1 = dirStream(getDirectory(mediaType))) {
            for (Path dir : ds1) {
                try (DirectoryStream<Path> subDirs = dirStream(dir)) {
                    for (Path subDir : subDirs) {
                        results.add(getUrl(subDir, name, mediaType));
                    }
                } catch (IOException e) {
                    log.$(error, e);
                }
            }
        } catch (IOException e) {
            log.$(error, e);
        }
        return results;
    }

    private static DirectoryStream<Path> dirStream(Path mediaDir)
        throws IOException {
        return Files.newDirectoryStream(mediaDir, DIRS_ONLY);
    }

    @Override
    public Path getStorageDir(MediaType mediaType, Language language,
                              String category) {
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
