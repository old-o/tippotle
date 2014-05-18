package net.doepner.resources;

import net.doepner.lang.Language;

import java.io.File;
import java.nio.file.Path;

/**
 * TODO: Document this!
 */
public interface ResourceDownloader {

    File download(Language language, String name, Path targetDir);
}
