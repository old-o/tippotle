package net.doepner.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.doepner.log.Log;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.isFileTypeSupported;

/**
 * Default audio player that delegates to the appropriate stream player
 */
public class StdAudioPlayer implements AudioPlayer {

    private final AudioStreamPlayer directPlayer = new DirectStreamPlayer();
    private final AudioStreamPlayer convertingPlayer = new ConvertingStreamPlayer();

    private final Log log;

    public StdAudioPlayer(Log log) {
        this.log = log;
    }

    @Override
    public void play(final URL url) throws UnsupportedAudioFileException, IOException {
        final AudioFileFormat format = getAudioFileFormat(url);

        final AudioStreamPlayer player = isFileTypeSupported(format.getType())
            ? directPlayer : convertingPlayer;

        if (player.isPlaybackBlockingThread()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    play(player, url);
                }
            }).start();

        } else {
            play(player, url);
        }
    }

    private void play(AudioStreamPlayer player, URL url) {
        try (AudioInputStream stream = getAudioInputStream(url)) {

            player.play(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
