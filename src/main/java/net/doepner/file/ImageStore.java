package net.doepner.file;

import java.io.File;

/**
 * A facility that can locate image files
 */
public interface ImageStore {

    File findImageFile(String name);
}
