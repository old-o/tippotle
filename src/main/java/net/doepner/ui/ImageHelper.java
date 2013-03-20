package net.doepner.ui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.doepner.file.ImageFiles;

/**
 * Helps with loading images
 */
public class ImageHelper {

    private final ImageFiles files;

    public ImageHelper(ImageFiles files) {
        this.files = files;
    }

    public Image getImage(String word) {
        if (word != null) {
            final String name = word.toLowerCase();
            final File imageFile = files.findImageFile(name);
            if (imageFile != null) {
                try {
                    return ImageIO.read(imageFile);
                } catch (IOException e) {
                    // ignored
                }
            }
        }
        return null;
    }
}
