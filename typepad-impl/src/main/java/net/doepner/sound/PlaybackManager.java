package net.doepner.sound;

import javax.sound.sampled.DataLine;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * TODO: Document this!
 */
public class PlaybackManager<T extends DataLine> {

    private final Collection<T> clipsPlaying = new ConcurrentLinkedDeque<>();
}
