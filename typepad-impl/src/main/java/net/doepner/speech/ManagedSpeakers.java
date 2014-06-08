package net.doepner.speech;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import javax.swing.SwingWorker;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static net.doepner.log.Log.Level.warn;
import static net.doepner.util.ComparisonUtil.not;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public class ManagedSpeakers implements Speaker {

    private final Iterable<Speaker> speakers;

    private Iterator<Speaker> speakerIter;
    private Speaker speaker;

    public ManagedSpeakers(LogProvider logProvider,
                           TestableSpeaker... speakers) {
        final Log log = logProvider.getLog(getClass());

        final List<Speaker> speakerList = new LinkedList<>();
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
        return speaker == null ? "unknown" : speaker.getName();
    }

    @Override
    public void speak(final String text) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                speaker.speak(text);
                return null;
            }
        }.execute();
    }

    public void nextSpeaker() {
        if (speakerIter == null || not(speakerIter.hasNext())) {
            speakerIter = speakers.iterator();
        }
        speaker = speakerIter.next();
    }
}
