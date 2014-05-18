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
    public URL find(MediaType mediaType, Language language, String category, String name) {

        final URL storedResource = store.find(mediaType, language, category, name);
        if (storedResource != null) {
            return storedResource;
        }
        final URL resource = finder.find(mediaType, language, category, name);
        if (resource != null) {
            return resource;
        }

        final Path targetDir = store.getStorageDir(mediaType, language, category);
        downloader.download(language, name, targetDir);

        return store.find(mediaType, language, category, name);
    }
}
