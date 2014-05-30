package net.doepner.speech;

import javax.swing.SwingWorker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public class ManagedSpeakers implements Speaker {

    private final List<Speaker> speakers;
    private Speaker speaker;

    public ManagedSpeakers(Collection<Speaker> speakers) {
        this.speakers = new ArrayList<>(speakers);
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
        speaker = speakers.get((speakers.indexOf(speaker) + 1) % speakers.size());
    }
}
