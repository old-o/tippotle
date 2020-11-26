package org.oldo.resources;

import org.oldo.file.FileInfo;
import org.oldo.lang.Language;

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
