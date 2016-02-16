package net.doepner.resources;

import net.doepner.file.ApplicationFiles;
import org.guppy4j.io.FileType;
import org.guppy4j.io.MediaType;
import net.doepner.lang.Language;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.guppy4j.log.Log.Level.info;

/**
 * Downloads files from URLs to the local filesystem
 */
public final class FileDownload implements ResourceDownloader {

    private static final Pattern UNDERSCORE = Pattern.compile("_");

    private final Log log;
    private final ApplicationFiles applicationFiles;
    private final UrlProvider urlProvider;

    public FileDownload(LogProvider logProvider,
                        ApplicationFiles applicationFiles,
                        UrlProvider urlProvider) {
        this.applicationFiles = applicationFiles;
        this.urlProvider = urlProvider;
        log = logProvider.getLog(getClass());
    }

    @Override
    public Path download(Language language, String name, Path targetDir) {
        final URL url;
        try {
            url = urlProvider.url(UNDERSCORE.matcher(name).replaceAll(" "), language);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Retrieving {}", url);
        try {
            final URLConnection c = url.openConnection();
            urlProvider.prepareConnection(c);
            return applicationFiles.writeFile(name, targetDir, c.getInputStream(), fileType());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public FileType fileType() {
        return urlProvider.fileType();
    }

    @Override
    public boolean supports(MediaType mediaType) {
        return fileType().mediaType().equals(mediaType);
    }
}
