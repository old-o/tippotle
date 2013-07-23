package net.doepner.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Plays wav audio files
 */
public class WavPlayer implements AudioPlayer {

    @Override
    public void play(File wavFile) {
        try (final AudioInputStream in = getAudioInputStream(wavFile)) {
            final Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
            clip.drain();
        } catch (UnsupportedAudioFileException | LineUnavailableException
            | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
