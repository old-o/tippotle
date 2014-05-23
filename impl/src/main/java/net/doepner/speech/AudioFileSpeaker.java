package net.doepner.speech;

import net.doepner.lang.Language;
import net.doepner.lang.LanguageProvider;
import net.doepner.log.LogProvider;
import net.doepner.resources.ResourceFinder;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static net.doepner.file.MediaTypeEnum.audio;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements TestableSpeaker {

    private final String speakerName;
    private final ResourceFinder resourceFinder;
    private final LanguageProvider languageProvider;
    private final AudioPlayer player;

    public AudioFileSpeaker(String speakerName, LogProvider logProvider,
                            ResourceFinder resourceFinder,
                            LanguageProvider languageProvider) {
        this.speakerName = speakerName;
        this.resourceFinder = resourceFinder;
        this.languageProvider = languageProvider;
        player = new StdAudioPlayer(logProvider);
    }

    @Override
    public String getName() {
        return speakerName;
    }

    @Override
    public void speak(final String text) {
        final Language language = languageProvider.getLanguage();
        final URL audioFile = resourceFinder.find(audio, text, language, speakerName);
        try {
            player.play(audioFile);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void test() {
        speak(getName());
    }
}

