package net.doepner.text;

import org.guppy4j.event.ChangeListener;

import java.util.function.Consumer;

import static net.doepner.ui.SwingUtil.doInBackground;
import static org.guppy4j.BaseUtil.bothNullOrEqual;
import static org.guppy4j.BaseUtil.not;

/**
 * Test position listener that invokes character and/or word consumers
 * if a change occurred
 */
public final class CurrentTextTracker implements ChangeListener<Integer> {

    private final WordProvider wordProvider;
    private final Consumer<Character> charConsumer;
    private final Consumer<TextSpan>[] wordConsumers;

    @SafeVarargs
    public CurrentTextTracker(WordProvider wordProvider,
                              Consumer<Character> charConsumer,
                              Consumer<TextSpan>... wordConsumers) {
        this.wordProvider = wordProvider;
        this.charConsumer = charConsumer;
        this.wordConsumers = wordConsumers;
    }

    @Override
    public void handleChange(final Integer before, final Integer after) {
        doInBackground(() -> {
            final Character ch = wordProvider.getCharacter(after);
            if (not(bothNullOrEqual(ch, wordProvider.getCharacter(before)))) {
                charConsumer.accept(ch);
            }
            final TextSpan textSpan = wordProvider.getWord(after);
            final String word = textSpan.getContent();
            if ((word == null || word.trim().length() != 1)
                    && not(bothNullOrEqual(word, wordProvider.getWord(before)))) {
                for (Consumer<TextSpan> consumer : wordConsumers) {
                    consumer.accept(textSpan);
                }
            }
        });
    }
}


