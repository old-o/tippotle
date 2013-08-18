package net.doepner.speech;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import javax.sound.sampled.UnsupportedAudioFileException;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;
import net.doepner.resources.DelegatingResourceFinder;
import net.doepner.resources.FileFinder;
import net.doepner.resources.GoogleTranslateDownload;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements Speaker {

    private final String[] extensions = {"ogg", "mp3", "wav", "au"};

    private final AudioPlayer player = new StdAudioPlayer();
    private final DelegatingResourceFinder resourceFinder;

    public AudioFileSpeaker(PathHelper pathHelper, LanguageProvider languageProvider) {
        final Path audioDir = pathHelper.findOrCreate("audio", DIRECTORY);
        resourceFinder = new DelegatingResourceFinder(
            //new ClasspathFinder(extensions),
            new FileFinder(pathHelper, languageProvider, audioDir, extensions),
            new GoogleTranslateDownload(languageProvider, audioDir)
        );
    }

    @Override
    public String getName() {
        return "jill";
    }

    @Override
    public void speak(final String text) {
        for (String s : text.toLowerCase().split("[^\\w']+")) {
            speakPart(s);
        }
    }

    private void speakPart(String text) {
        final URL audio = resourceFinder.find(text);
        if (audio != null) {
            try {
                player.play(audio);
            } catch (IOException | UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

