package net.doepner.resources;

import net.doepner.file.FileInfo;
import net.doepner.lang.Language;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Constructs URLs for file downloads
 */
public interface UrlProvider extends FileInfo {

    URL url(String s, Language language) throws MalformedURLException;

    void prepareConnection(URLConnection c);

}
