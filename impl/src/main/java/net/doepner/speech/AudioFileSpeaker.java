package net.doepner.speech;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;
import net.doepner.log.LogProvider;
import net.doepner.resources.ClasspathFinder;
import net.doepner.resources.DelegatingResourceFinder;
import net.doepner.resources.FileFinder;
import net.doepner.resources.GoogleTranslateDownload;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements TestableSpeaker {

    private static final String[] EXTENSIONS = {"ogg", "mp3", "wav", "au"};

    private final DelegatingResourceFinder resourceFinder;
    private final AudioPlayer player;

    public AudioFileSpeaker(PathHelper pathHelper,
                            LanguageProvider languageProvider,
                            LogProvider logProvider) {

        final Path audioDir = pathHelper.findOrCreate("audio", DIRECTORY);
        resourceFinder = new DelegatingResourceFinder(
                new ClasspathFinder(EXTENSIONS),
                new FileFinder(pathHelper, languageProvider, audioDir, EXTENSIONS),
                new GoogleTranslateDownload(languageProvider, audioDir, logProvider)
        );
        player = new StdAudioPlayer(logProvider);
    }

    @Override
    public String getName() {
        return "jill";
    }

    @Override
    public void speak(final String text) {
/*
        for (String s : text.toLowerCase().split("[^\\w']+")) {
            speakPart(s);
        }
*/
        speakPart(text);
    }

    private void speakPart(String text) {
        final URL audio = resourceFinder.find(text);
        if (audio != null) {
            try {
                player.play(audio);
            } catch (IOException | UnsupportedAudioFileException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void test() {
        speak(getName());
    }
}

