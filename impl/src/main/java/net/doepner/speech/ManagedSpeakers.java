package net.doepner.speech;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static net.doepner.ui.SwingUtil.doInBackground;
import static org.guppy4j.BaseUtil.not;
import static org.guppy4j.log.Log.Level.warn;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public final class ManagedSpeakers implements IterableSpeakers {

    private final Iterable<Speaker> speakers;

    private Iterator<Speaker> iterator;
    private Speaker current;

    public ManagedSpeakers(LogProvider logProvider,
                           TestableSpeaker... speakers) {
        final Log log = logProvider.getLog(getClass());

        final Collection<Speaker> speakerList = new LinkedList<>();
        for (TestableSpeaker speaker : speakers) {
            try {
                speaker.test();
                speakerList.add(speaker);

            } catch (IllegalStateException e) {
                log.as(warn, "Speaker '{}' not functional. Error: {}",
                        speaker.name(), e.getMessage());
            }
        }
        this.speakers = speakerList;
        next();
    }

    @Override
    public String name() {
        return current == null ? "unknown" : current.name();
    }

    @Override
    public void stopAll() {
        speakers.forEach(s -> s.stopAll());
    }

    @Override
    public void speak(final String text) {
        doInBackground(() -> current.speak(text));
    }

    @Override
    public void next() {
        if (iterator == null || not(iterator.hasNext())) {
            iterator = speakers.iterator();
        }
        current = iterator.next();
    }
}
