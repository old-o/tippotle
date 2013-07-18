package net.doepner.file;

import java.io.File;
import java.nio.file.Path;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Helps with finding images
 */
public class ImageFiles implements ImageStore {

    private static final String[] IMAGE_EXTENSIONS = {"png", "jpg", "gif"};

    private final IFileHelper fileHelper;
    private final Path imgDir;

    public ImageFiles(IFileHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.imgDir = fileHelper.findOrCreate("images", DIRECTORY);
    }

    @Override
    public File findImageFile(String name) {
        for (String extension : IMAGE_EXTENSIONS) {
            final File file = findInDir(name, extension);
            if (file != null) {
                return file;
            }
        }
        return null;
    }

    private File findInDir(String name, String extension) {
        return fileHelper.findInDir(imgDir, name, extension);
    }
}
