package net.doepner.sound;

import java.io.IOException;

import javax.sound.sampled.*;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Plays wav audio files
 */
public class WavPlayer extends AbstractAudioPlayer {

    @Override
    protected void play(AudioInputStream stream)
            throws LineUnavailableException, IOException {
        final Clip clip = AudioSystem.getClip();
        clip.open(stream);
        clip.drain();
        clip.stop();
    }
}
