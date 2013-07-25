package net.doepner.sound;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Base class for audio players
 */
public abstract class AbstractAudioPlayer implements AudioPlayer {

    private final List<String> mimeTypes;

    public AbstractAudioPlayer(String... mimeTypes) {
        this.mimeTypes = Arrays.asList(mimeTypes);
    }

    @Override
    public boolean canPlay(Path path) {
        try {
            return mimeTypes.contains(Files.probeContentType(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void play(final Path path) {
        if (playInNewThread()) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    doPlay(path);
                }
            });
            thread.setDaemon(true);
            thread.start();
        } else {
            doPlay(path);
        }
    }

    private void doPlay(Path path) {
        try (AudioInputStream stream = getAudioInputStream(path.toFile())) {
            play(stream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void play(AudioInputStream stream)
            throws LineUnavailableException, IOException;

    protected abstract boolean playInNewThread();
}
