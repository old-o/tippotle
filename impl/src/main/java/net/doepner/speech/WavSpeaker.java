package net.doepner.speech;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.doepner.file.IFileHelper;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing wav files
 */
public class WavSpeaker implements Speaker {

    private final IFileHelper fileHelper;
    private final Path audioDir;

    public WavSpeaker(IFileHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.audioDir = fileHelper.findOrCreate("audio", DIRECTORY);
    }

    @Override
    public void speak(final String text) {
        final File wavFile = fileHelper.findInDir(audioDir, text, "wav");
        if (wavFile != null) {
            final Clip clip = getClip(getAudioInputStream(wavFile));
            ensureCloseOnStop(clip);
            clip.start();
        }
    }

    private void ensureCloseOnStop(final Clip clip) {
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (LineEvent.Type.STOP.equals(event.getType())) {
                    clip.close();
                }
            }
        });
    }

    private AudioInputStream getAudioInputStream(File wavFile) {
        try {
            return AudioSystem.getAudioInputStream(wavFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Clip getClip(AudioInputStream stream) {
        try {
            final Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
