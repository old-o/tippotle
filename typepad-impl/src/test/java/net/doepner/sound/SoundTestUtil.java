package net.doepner.sound;

import net.doepner.log.SystemConsoleLogProvider;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manual audio player testing
 */
public class SoundTestUtil {

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException {
        System.out.println("Available mixers:");
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            System.out.println(info.getName());
        }

        final SystemConsoleLogProvider logProvider = new SystemConsoleLogProvider();

        final AudioPlayer player = new StdAudioPlayer(
                logProvider, new DirectStreamPlayer(), new ConvertingStreamPlayer(logProvider));

        final Path path = Paths.get(
//            "/home/oliver/files/test.m4a");
//                "C:/Documents and Settings/isdc858/Desktop/example.ogg");
//                "C:/Documents and Settings/isdc858/Desktop/blah.mp3");
//                "C:/WINDOWS/Media/Windows XP Startup.wav");
//                "/home/oliver/bubba/storage/music/aztec_camera/knife/all_i_need_is_everything.ogg"
//                "/home/oliver/bubba/storage/music/crash_test_dummies/god_shuffled_his_feet/mmm_mmm_mmm_mmm.ogg"
                "/home/oliver/bubba/storage/music/peter_bjorn_john/Peter_Bjorn_and_John_-_Young_Folks.mp3"
        );
//                  "/yazoo/Upstairs_At_Eric_s/01-Don_t_Go.ogg");
//                "/arbeit/an_den_deutschen_mond/ich_stand_auf_hohem_berge.wav");

//        player.play(new URL("http://ssl.gstatic.com/dictionary/static/sounds/de/0/supercalifragilisticexpialidocious.mp3"));

        player.play(path.toUri().toURL());

        Thread.sleep(10000);

//        player.play(path);
//        Thread.sleep(5000);
    }
}
