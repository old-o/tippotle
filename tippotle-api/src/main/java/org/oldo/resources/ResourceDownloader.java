package org.oldo.resources;

import org.guppy4j.io.FileType;
import org.guppy4j.io.MediaType;
import org.oldo.lang.Language;

import java.nio.file.Path;

/**
 * Downloads files of a certain type to the local filesystem
 */
public interface ResourceDownloader {

    Path download(Language language, String name, Path targetDir);

    FileType fileType();

    boolean supports(MediaType mediaType);
}
