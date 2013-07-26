package net.doepner.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;

/**
 * Plays audio streams
 */
public interface AudioStreamPlayer {

    boolean isPlaybackBlockingThread();

    void play(AudioInputStream stream) throws LineUnavailableException, IOException;
}
