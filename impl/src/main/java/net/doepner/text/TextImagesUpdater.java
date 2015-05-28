package net.doepner.text;

import net.doepner.ui.ImagesContainer;
import org.guppy4j.event.ChangeListener;

import java.awt.Image;
import java.util.function.Function;

import static net.doepner.ui.SwingUtil.doInBackground;
import static org.guppy4j.BaseUtil.bothNullOrEqual;
import static org.guppy4j.BaseUtil.not;

/**
 * Test position listener that updates images
 */
public final class TextImagesUpdater implements ChangeListener<Integer> {

    private final WordProvider wordProvider;

    private final ImagesContainer charImageBar;
    private final ImagesContainer wordImageBar;

    private final Function<String, Iterable<Image>> imageCollector;

    public TextImagesUpdater(WordProvider wordProvider,
                             ImagesContainer charImageBar,
                             ImagesContainer wordImageBar,
                             Function<String, Iterable<Image>> imageCollector) {
        this.wordProvider = wordProvider;
        this.charImageBar = charImageBar;
        this.wordImageBar = wordImageBar;
        this.imageCollector = imageCollector;
    }

    @Override
    public void handleChange(final Integer before, final Integer after) {
        doInBackground(() -> {
            final Character ch = wordProvider.character(after);
            if (not(bothNullOrEqual(ch, wordProvider.character(before)))) {
                charImageBar.setImages(imageCollector.apply(String.valueOf(ch)));
            }
            final String word = wordProvider.word(after);
            if ((word == null || word.trim().length() != 1)
                    && not(bothNullOrEqual(word, wordProvider.word(before)))) {
                wordImageBar.setImages(imageCollector.apply(word));
            }
        });
    }
}


