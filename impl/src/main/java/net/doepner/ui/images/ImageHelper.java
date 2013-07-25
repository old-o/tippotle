package net.doepner.ui.images;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import net.doepner.file.PathHelper;
import net.doepner.ui.Images;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Helps with loading images
 */
public class ImageHelper implements Images {

    private final PathHelper fileHelper;
    private final Path imgDir;

    public ImageHelper(PathHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.imgDir = fileHelper.findOrCreate("images", DIRECTORY);
    }

    @Override
    public Image getImage(String word) {
        if (word == null) {
            return null;
        }
        final String name = word.toLowerCase();

        final Path imagePath = fileHelper.findInDir(imgDir, name, "png", "jpg", "gif");
        if (imagePath == null) {
            return null;
        }
        try {
            return ImageIO.read(imagePath.toFile());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}