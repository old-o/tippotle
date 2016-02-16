package net.doepner.resources;

import net.doepner.lang.Language;
import net.doepner.util.StringNormalizer;
import org.guppy4j.io.MediaType;

import java.net.URL;
import java.nio.file.Path;

/**
 * Finds files in classpath, file system or online
 */
public final class CascadingResourceFinder implements ResourceFinder {

    private final ResourceStore store;
    private final ResourceFinder finder;
    private final ResourceDownloader downloader;

    private final StringNormalizer nameNormalizer;

    public CascadingResourceFinder(ResourceStore store,
                                   ResourceFinder finder,
                                   ResourceDownloader downloader,
                                   StringNormalizer nameNormalizer) {
        this.store = store;
        this.finder = finder;
        this.downloader = downloader;
        this.nameNormalizer = nameNormalizer;
    }

    @Override
    public URL find(MediaType mediaType, String rawName,
                    Language language, String category) {

        if (rawName.trim().isEmpty()) {
            return null;
        }

        final String name = nameNormalizer.normalize(rawName);

        final URL storedResource = store.find(mediaType, name, language, category);
        if (storedResource != null) {
            return storedResource;
        }

        final URL resource = finder.find(mediaType, name, language, category);
        if (resource != null) {
            return resource;
        }

        if (downloader != null && downloader.supports(mediaType)) {
            final Path targetDir = store.storageDir(mediaType, language, category);
            downloader.download(language, name, targetDir);
            return store.find(mediaType, name, language, category);
        }

        //otherwise
        return null;
    }
}
