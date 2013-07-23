package net.doepner.sound;

import java.io.File;

/**
 * Plays audio files (like wav, mp3, etc.)
 */
public interface AudioPlayer {

    void play(File file);
}
