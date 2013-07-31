package net.doepner.speech;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;
import net.doepner.resources.ResourceFinder;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.StdAudioPlayer;

/**
 * Speaks by playing audio files
 */
public class AudioFileSpeaker implements Speaker {

    private final AudioPlayer player = new StdAudioPlayer();
    private final ResourceFinder resourceFinder;

    public AudioFileSpeaker(PathHelper pathHelper, LanguageProvider languageProvider) {
        resourceFinder = new ResourceFinder(pathHelper, "audio", languageProvider,
                "ogg", "mp3", "wav", "au");
    }

    @Override
    public String getName() {
        return "recording";
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

