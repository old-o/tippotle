package net.doepner.sound;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 * Manual audio player testing
 */
public class SoundTestUtil {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Available mixers:");
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            System.out.println(info.getName());
        }

//        final Mp3Player player = new Mp3Player();
        final WavPlayer player = new WavPlayer();

        final Path path = Paths.get(
//                "C:/Documents and Settings/isdc858/Desktop/blah.mp3");
                "C:/WINDOWS/Media/Windows XP Startup.wav");
//                 "/home/oliver/bubba/storage/music" +
//                "/peter_bjorn_john/Peter_Bjorn_and_John_-_Young_Folks.mp3");
//                "/arbeit/an_den_deutschen_mond/ich_stand_auf_hohem_berge.wav");

        player.play(path);
        Thread.sleep(1000);

        player.play(path);
        Thread.sleep(5000);
    }
}
