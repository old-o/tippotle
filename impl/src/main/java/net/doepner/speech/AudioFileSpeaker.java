package net.doepner.speech;

import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.UnsupportedAudioFileException;

import net.doepner.file.PathHelper;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements Speaker {

    private final AudioPlayer player = new StdAudioPlayer();

    private final PathHelper pathHelper;
    private final Path audioDir;

    public AudioFileSpeaker(PathHelper pathHelper) {
        this.pathHelper = pathHelper;
        this.audioDir = pathHelper.findOrCreate("audio", DIRECTORY);
    }

    @Override
    public void speak(final String text) {
        final Path path = pathHelper.findInDir(audioDir, text, "ogg", "mp3", "wav", "au");
        if (path != null) {
            try {
                player.play(path);
            } catch (IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
