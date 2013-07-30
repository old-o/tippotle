package net.doepner.speech;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Delegates to the currently selected speaker (among the available ones)
 */
public class SelectableSpeaker implements Speaker, SpeakerChanger {

    private final List<Speaker> speakers;
    private Speaker speaker;

    public SelectableSpeaker(Collection<Speaker> speakers) {
        this.speakers = new ArrayList<>(speakers);
        nextSpeaker();
    }

    @Override
    public void speak(String text) {
        speaker.speak(text);
    }

    @Override
    public void nextSpeaker() {
        speaker = speakers.get((speakers.indexOf(speaker) + 1) % speakers.size());
    }
}
