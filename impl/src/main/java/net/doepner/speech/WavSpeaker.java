package net.doepner.speech;

import java.io.File;
import java.nio.file.Path;

import net.doepner.file.IFileHelper;
import net.doepner.sound.WavPlayer;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing wav files
 */
public class WavSpeaker implements Speaker {

    private final WavPlayer wavPlayer = new WavPlayer();

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
            wavPlayer.play(wavFile);
        }
    }
}
