package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.nio.file.Path;

/**
 * Downloads files of a certain type to the local filesystem
 */
public interface ResourceDownloader {

    Path download(Language language, String name, Path targetDir);

    FileType fileType();

    boolean supports(MediaType mediaType);
}
