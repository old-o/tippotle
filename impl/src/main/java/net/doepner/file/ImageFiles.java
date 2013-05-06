package net.doepner.file;

import java.io.File;
import java.nio.file.Path;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Helps with finding images
 */
public class ImageFiles implements ImageStore {

    private final Path imgDir;

    private final IFileHelper fileHelper;

    public ImageFiles(IFileHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.imgDir = fileHelper.findOrCreate("images", DIRECTORY);
    }

    @Override
    public File findImageFile(String name) {
        final File png = findInDir(name, "png");
        return png != null ? png : findInDir(name, "jpg");
    }

    private File findInDir(String name, String extension) {
        return fileHelper.findInDir(imgDir, name, extension);
    }
}
