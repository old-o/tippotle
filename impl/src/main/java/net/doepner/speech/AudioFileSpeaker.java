package net.doepner.speech;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import net.doepner.file.PathHelper;
import net.doepner.sound.AudioPlayer;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements Speaker {

    private final List<AudioPlayer> players;

    private final PathHelper pathHelper;
    private final Path audioDir;

    public AudioFileSpeaker(PathHelper pathHelper, AudioPlayer... players) {
        this.pathHelper = pathHelper;
        this.audioDir = pathHelper.findOrCreate("audio", DIRECTORY);
        this.players = Arrays.asList(players);
    }

    @Override
    public void speak(final String text) {
        final Path path = pathHelper.findInDir(audioDir, text, "ogg", "mp3", "wav");
        if (path != null) {
            for (AudioPlayer player : players) {
                if (player.canPlay(path)) {
                    player.play(path);
                    return;
                }
            }
        }
    }
}
