package net.doepner.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Plays audio files (like wav, mp3, etc.)
 */
public interface AudioPlayer {

    void play(URL url) throws IOException, UnsupportedAudioFileException;
}
