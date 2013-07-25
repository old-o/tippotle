package net.doepner.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

/**
 * Plays wav audio files
 */
public class WavPlayer extends AbstractAudioPlayer {

    @Override
    protected void play(AudioInputStream stream)
            throws LineUnavailableException, IOException {

        final Clip clip = (Clip) AudioSystem.getLine(
                new DataLine.Info(Clip.class, stream.getFormat()));

        clip.open(stream);
        clip.start();

        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (LineEvent.Type.STOP.equals(event.getType())) {
                    clip.close();
                }
            }
        });
    }

    @Override
    protected boolean playInNewThread() {
        return false;
    }
}
