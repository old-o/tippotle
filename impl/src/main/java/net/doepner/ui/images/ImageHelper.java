package net.doepner.ui.images;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.doepner.file.ImageStore;
import net.doepner.ui.ImageMap;

/**
 * Helps with loading images
 */
public class ImageHelper implements ImageMap {

    private final ImageStore files;

    public ImageHelper(ImageStore files) {
        this.files = files;
    }

    @Override
    public Image getImage(String word) {
        if (word == null) {
            return null;
        }
        final String name = word.toLowerCase();

        final File imageFile = files.findImageFile(name);
        if (imageFile == null) {
            return null;
        }
        try {
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}