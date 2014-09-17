package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.FileTypeEnum;
import net.doepner.lang.Language;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Constructs URLs for spoken word from Google Translate site,
 * with language-specific pronunciation
 */
public final class GoogleTranslateUrls implements UrlProvider {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:21.0) " +
            "Gecko/20100101 Firefox/21.0 Iceweasel/21.0";

    public URL getUrl(String name, Language language) throws MalformedURLException {
        return new URL("http://translate.google.com/translate_tts?ie=UTF-8"
                + (language != null ? "&tl=" + urlEncode(language.getCode()) : "")
                + "&q=" + urlEncode(name.toLowerCase()));
    }

    @Override
    public void configure(URLConnection c) {
        c.setRequestProperty("User-Agent", USER_AGENT);
    }

    @Override
    public FileType getFileType() {
        return FileTypeEnum.mp3;
    }

    private static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
