package net.doepner.speech;

import net.doepner.file.MediaTypeEnum;
import net.doepner.lang.Language;
import net.doepner.lang.LanguageProvider;
import net.doepner.log.LogProvider;
import net.doepner.resources.ResourceFinder;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements TestableSpeaker {

    private final String name;
    private final ResourceFinder resourceFinder;
    private final LanguageProvider languageProvider;
    private final AudioPlayer player;

    public AudioFileSpeaker(String name, LogProvider logProvider,
                            ResourceFinder resourceFinder,
                            LanguageProvider languageProvider) {
        this.name = name;
        this.resourceFinder = resourceFinder;
        this.languageProvider = languageProvider;
        player = new StdAudioPlayer(logProvider);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void speak(final String text) {
        final Language language = languageProvider.getLanguage();
        final URL audio = resourceFinder.find(MediaTypeEnum.audio, language, getName(), text);
        try {
            player.play(audio);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void test() {
        speak(getName());
    }
}

