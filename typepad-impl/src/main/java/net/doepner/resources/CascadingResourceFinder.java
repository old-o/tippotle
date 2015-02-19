package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Finds files in classpath, file system or online
 */
public final class CascadingResourceFinder implements ResourceFinder {

    private final ResourceStore store;
    private final ResourceFinder finder;
    private final ResourceDownloader downloader;

    public CascadingResourceFinder(ResourceStore store,
                                   ResourceFinder finder) {
        this(store, finder, null);
    }

    public CascadingResourceFinder(ResourceStore store,
                                   ResourceFinder finder,
                                   ResourceDownloader downloader) {
        this.store = store;
        this.finder = finder;
        this.downloader = downloader;
    }

    @Override
    public URL find(MediaType mediaType, String rawName,
                    Language language, String category) {

        if (rawName.trim().isEmpty()) {
            return null;
        }

        final String name = normalize(rawName);

        final URL storedResource = store.find(mediaType, name, language, category);
        if (storedResource != null) {
            return storedResource;
        }

        final URL resource = finder.find(mediaType, name, language, category);
        if (resource != null) {
            return resource;
        }

        if (downloader != null
                && downloader.getFileType().getMediaType().equals(mediaType)) {

            final Path targetDir = store.getStorageDir(mediaType, language, category);
            downloader.download(language, name, targetDir);
            return store.find(mediaType, name, language, category);
        }

        //otherwise
        return null;
    }

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern NON_WORD_CHARS = Pattern.compile("\\W");

    private static String normalize(String rawName) {
        final String whitespaceCleaned =
                WHITESPACE.matcher(rawName.toLowerCase()).replaceAll(" ");
        return NON_WORD_CHARS.matcher(whitespaceCleaned).replaceAll("_");
    }
}
