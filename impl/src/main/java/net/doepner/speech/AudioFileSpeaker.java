package net.doepner.speech;

import net.doepner.lang.Language;
import net.doepner.lang.LanguageProvider;
import net.doepner.resources.ResourceFinder;
import org.guppy4j.sound.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static net.doepner.file.MediaTypeEnum.audio;

/**
 * Speaks by playing audio files
 */
public final class AudioFileSpeaker implements TestableSpeaker {

    private final String speakerName;
    private final ResourceFinder resourceFinder;
    private final LanguageProvider languageProvider;
    private final AudioPlayer audioPlayer;

    public AudioFileSpeaker(String speakerName,
                            ResourceFinder resourceFinder,
                            LanguageProvider languageProvider,
                            AudioPlayer audioPlayer) {
        this.speakerName = speakerName;
        this.resourceFinder = resourceFinder;
        this.languageProvider = languageProvider;
        this.audioPlayer = audioPlayer;
    }

    @Override
    public String name() {
        return speakerName;
    }

    @Override
    public void stopAll() {
        audioPlayer.stopAll();
    }

    @Override
    public void speak(final String text) {
        speakPart(text);
/*
        for (String s : text.toLowerCase().split("[^\\w']+")) {
            speakPart(s);
        }
*/
    }

    private void speakPart(String text) {
        final Language language = languageProvider.language();
        final URL audioFile = resourceFinder.find(audio, text, language, speakerName);
        try {
            audioPlayer.play(audioFile);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void test() {
        speak(speakerName);
    }
}

