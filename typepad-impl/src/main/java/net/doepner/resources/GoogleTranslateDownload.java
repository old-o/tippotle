package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.FileTypeEnum;
import net.doepner.file.PathHelper;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;

import static net.doepner.log.Log.Level.info;

/**
 * Downloads spoken word from Google Translate site,
 * with language-specific pronunciation
 */
public class GoogleTranslateDownload implements ResourceDownloader {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:21.0) " +
            "Gecko/20100101 Firefox/21.0 Iceweasel/21.0";

    private final Log log;
    private final PathHelper pathHelper;

    public GoogleTranslateDownload(LogProvider logProvider,
                                   PathHelper pathHelper) {
        this.pathHelper = pathHelper;
        log = logProvider.getLog(getClass());
    }

    @Override
    public File download(Language language, String name, Path targetDir) {
        try {
            final URL url = getDownloadUrl(name.replaceAll("_", " "), language);
            log.as(info, "Retrieving {}", url);

            final URLConnection c = url.openConnection();
            c.setRequestProperty("User-Agent", USER_AGENT);

            return pathHelper.writeFile(name, targetDir, c.getInputStream(), getFileType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileType getFileType() {
        return FileTypeEnum.mp3;
    }

    private URL getDownloadUrl(String name, Language language) throws MalformedURLException {
        return new URL("http://translate.google.com/translate_tts?ie=UTF-8" +
                (language != null ? "&tl=" + urlEncode(language.getCode()) : "") +
                "&q=" + urlEncode(name.toLowerCase()));
    }

    private String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
