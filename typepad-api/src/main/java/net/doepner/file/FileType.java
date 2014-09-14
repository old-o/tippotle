package net.doepner.file;

/**
 * A file type
 */
public interface FileType {

    MediaType getMediaType();

    String getExtension();

    String getMimeType();
}
