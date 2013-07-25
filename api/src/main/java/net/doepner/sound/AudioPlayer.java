package net.doepner.sound;

import java.nio.file.Path;

/**
 * Plays audio files (like wav, mp3, etc.)
 */
public interface AudioPlayer {

    boolean canPlay(Path path);

    void play(Path path);
}
