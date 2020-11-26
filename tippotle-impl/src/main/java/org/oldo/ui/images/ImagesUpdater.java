package org.oldo.ui.images;

import java.awt.Image;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Triggers image updates
 */
public final class ImagesUpdater<T> implements Consumer<T> {

    private final ImagesContainer imageBar;

    private final Function<T, Iterable<Image>> imageCollector;

    public ImagesUpdater(ImagesContainer imageBar,
                         Function<T, Iterable<Image>> imageCollector) {
        this.imageBar = imageBar;
        this.imageCollector = imageCollector;
    }

    @Override
    public void accept(T t) {
        imageBar.setImages(imageCollector.apply(t));
    }
}
