package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.lang.Language;

import java.io.File;
import java.nio.file.Path;

/**
 * Downloads files of a certain type to the local filesystem
 */
public interface ResourceDownloader {

    File download(Language language, String name, Path targetDir);

    FileType getFileType();
}
