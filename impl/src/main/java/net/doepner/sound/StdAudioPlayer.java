package net.doepner.sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.isFileTypeSupported;

/**
 * Default audio player that delegates to the appropriate stream player
 */
public class StdAudioPlayer implements AudioPlayer {

    private final AudioStreamPlayer directPlayer = new DirectStreamPlayer();
    private final AudioStreamPlayer convertingPlayer = new ConvertingStreamPlayer();

    @Override
    public void play(final Path path) throws UnsupportedAudioFileException, IOException {
        final File file = path.toFile();
        final AudioFileFormat format = getAudioFileFormat(file);

        final AudioStreamPlayer player = isFileTypeSupported(format.getType())
            ? directPlayer : convertingPlayer;

        if (player.isPlaybackBlockingThread()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    play(player, file);
                }
            }).start();

        } else {
            play(player, file);
        }
    }

    private void play(AudioStreamPlayer player, File file) {
        try (AudioInputStream stream = getAudioInputStream(file)) {

            player.play(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
