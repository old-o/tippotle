package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Helps with finding images
 */
public class ImageFiles implements ImageStore {

    private final Path imgDir;

    private final IFileHelper fileHelper;

    public ImageFiles(IFileHelper fileHelper) {
        this.fileHelper = fileHelper;
        try {
            this.imgDir = fileHelper.getAppSubDirPath("images");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File findImageFile(String name) {
        final File png = fileHelper.findFile(imgDir, name, "png");
        return png != null ? png :  fileHelper.findFile(imgDir, name, "jpg");
    }
}
