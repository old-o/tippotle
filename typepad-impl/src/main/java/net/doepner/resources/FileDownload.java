package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.PathHelper;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static net.doepner.log.Log.Level.info;

/**
 * Downloads files from URLs to the local filesystem
 */
public final class FileDownload implements ResourceDownloader {

    private static final Pattern UNDERSCORE = Pattern.compile("_");

    private final Log log;
    private final PathHelper pathHelper;
    private final UrlProvider urlProvider;

    public FileDownload(LogProvider logProvider,
                        PathHelper pathHelper,
                        UrlProvider urlProvider) {
        this.pathHelper = pathHelper;
        this.urlProvider = urlProvider;
        log = logProvider.getLog(getClass());
    }

    @Override
    public File download(Language language, String name, Path targetDir) {
        final URL url;
        try {
            url = urlProvider.getUrl(UNDERSCORE.matcher(name).replaceAll(" "), language);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Retrieving {}", url);
        try {
            final URLConnection c = url.openConnection();
            urlProvider.configure(c);
            return pathHelper.writeFile(name, targetDir, c.getInputStream(), getFileType());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public FileType getFileType() {
        return urlProvider.getFileType();
    }


}
