package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.lang.Language;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Constructs URLs for file downloads
 */
public interface UrlProvider {

    URL getUrl(String s, Language language) throws MalformedURLException;

    void configure(URLConnection c);

    FileType getFileType();
}
