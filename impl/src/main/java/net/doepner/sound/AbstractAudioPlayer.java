package net.doepner.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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
    public void play(Path path) {
        try (AudioInputStream stream = getAudioInputStream(path.toFile())) {

            play(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException
                | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void play(AudioInputStream stream)
            throws LineUnavailableException, IOException;
}
