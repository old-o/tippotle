package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Helps with finding images
 */
public class ImageFiles {

    private final Path imgDir;

    private final FileHelper fileHelper;

    public ImageFiles(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
        try {
            this.imgDir = fileHelper.getAppDirPath("images");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File findImageFile(String name) {
        final File png = fileHelper.findFile(imgDir, name, "png");
        if (png != null) {
            return png;
        }
        final File jpg = fileHelper.findFile(imgDir, name, "jpg");
        if (jpg != null) {
            return jpg;
        }
        return null;
    }
}
