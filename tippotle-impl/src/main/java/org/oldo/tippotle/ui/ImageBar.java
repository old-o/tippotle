package org.oldo.tippotle.ui;

import org.oldo.ui.images.ImageContainer;
import org.oldo.ui.images.ImagePanel;
import org.oldo.ui.images.ImagesContainer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.PAGE_AXIS;

/**
 * Supplies a panel with several images in vertical or horizontal arrangement
 */
public final class ImageBar implements Supplier<JPanel>, ImagesContainer {

    private final JPanel component = new JPanel();
    private final Iterable<? extends ImageContainer> imageContainers;

    public static ImageBar vertical(Dimension imageSize, int imageCount) {
        return new ImageBar(imageSize, PAGE_AXIS, imageCount);
    }

    public static ImageBar horizontal(Dimension imageSize, int imageCount) {
        return new ImageBar(imageSize, LINE_AXIS, imageCount);
    }

    private ImageBar(Dimension imageSize, int axis, int imageCount) {
        component.setLayout(new BoxLayout(component, axis));

        final Collection<ImagePanel> imagePanels = new ArrayList<>(imageCount);
        for (int i = 0; i < imageCount; i++) {
            final ImagePanel imagePanel = new ImagePanel(imageSize);
            component.add(imagePanel);
            imagePanels.add(imagePanel);
        }
        imageContainers = imagePanels;
    }

    @Override
    public void setImages(Iterable<Image> images) {
        final Iterator<Image> imageIter = images.iterator();
        for (ImageContainer panel : imageContainers) {
            panel.setImage(imageIter.next());
        }
    }

    @Override
    public JPanel get() {
        return component;
    }
}
