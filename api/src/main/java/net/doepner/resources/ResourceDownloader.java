package net.doepner.resources;

import net.doepner.lang.Language;
import org.guppy4j.io.FileType;
import org.guppy4j.io.MediaType;

import java.nio.file.Path;

/**
 * Downloads files of a certain type to the local filesystem
 */
public interface ResourceDownloader {

    Path download(Language language, String name, Path targetDir);

    FileType fileType();

    boolean supports(MediaType mediaType);
}
