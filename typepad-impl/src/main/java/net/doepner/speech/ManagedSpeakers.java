package net.doepner.speech;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.SwingWorker;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static org.guppy4j.BaseUtil.not;
import static org.guppy4j.log.Log.Level.warn;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public final class ManagedSpeakers implements IterableSpeakers {

    private final Iterable<Speaker> speakers;

    private Iterator<Speaker> speakerIter;
    private Speaker currentSpeaker;

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
                        speaker.getName(), e.getMessage());
            }
        }
        this.speakers = speakerList;
        nextSpeaker();
    }

    @Override
    public String getName() {
        return currentSpeaker == null ? "unknown" : currentSpeaker.getName();
    }

    @Override
    public void stopAll() {
        for (Speaker speaker : speakers) {
            speaker.stopAll();
        }
    }

    @Override
    public void speak(final String text) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                currentSpeaker.speak(text);
                return null;
            }
        }.execute();
    }

    @Override
    public void nextSpeaker() {
        if (speakerIter == null || not(speakerIter.hasNext())) {
            speakerIter = speakers.iterator();
        }
        currentSpeaker = speakerIter.next();
    }
}
