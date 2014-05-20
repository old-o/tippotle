package net.doepner.resources;

import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.net.URL;
import java.nio.file.Path;

/**
 * Finds files in classpath, file system or online
 */
public class CascadingResourceFinder implements ResourceFinder {

    private final ResourceStore store;
    private final ResourceFinder finder;
    private final ResourceDownloader downloader;

    public CascadingResourceFinder(ResourceStore store,
                                   ResourceFinder finder,
                                   ResourceDownloader downloader) {
        this.store = store;
        this.finder = finder;
        this.downloader = downloader;
    }

    @Override
    public URL find(String rawName, MediaType mediaType, Language language, String category) {
        final String name = normalize(rawName);

        final URL storedResource = store.find(name, mediaType, language, category);
        if (storedResource != null) {
            return storedResource;
        }

        final URL resource = finder.find(name, mediaType, language, category);
        if (resource != null) {
            return resource;
        }

        if (downloader.getFileType().getMediaType().equals(mediaType)) {
            final Path targetDir = store.getStorageDir(mediaType, language, category);
            downloader.download(language, name, targetDir);
            return store.find(name, mediaType, language, category);

        } else {
            return null;
        }
    }

    private String normalize(String rawName) {
        return rawName.toLowerCase().replaceAll("\\s+", " ");
    }
}
