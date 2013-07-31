package net.doepner.ui;

import java.awt.Image;

/**
 * Maps image names to images
 */
public interface Images {

    Iterable<Image> getImages(String word);
}
