package net.doepner.file;

/**
 * Media type, mainly for audio and image files
 */
public interface MediaType {

    boolean isAudio();

    boolean isImage();

    String groupingName();

    Iterable<FileType> fileTypes();
}