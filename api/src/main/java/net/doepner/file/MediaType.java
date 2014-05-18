package net.doepner.file;

/**
 * TODO: Document this!
 */
public interface MediaType {

    boolean isAudio();

    boolean isImage();

    String getGroupingName();

    Iterable<FileType> getFileTypes();
}
