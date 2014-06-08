package net.doepner.resources;

import java.awt.Image;

/**
 * Finds and gathers images by name
 */
public interface ImageCollector {

    Iterable<Image> getImages(String name);
}
