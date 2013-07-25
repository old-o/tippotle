package net.doepner.sound;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class Mp3Player extends AbstractAudioPlayer {

    public Mp3Player() {
        super("audio/mpeg");
    }

    public static void main(String[] args) {
        System.out.println("Available mixers:");
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            System.out.println(info.getName());
        }

        final Mp3Player player = new Mp3Player();
        final Path path = Paths.get("/home/oliver/bubba/storage/music" +
                "/peter_bjorn_john/Peter_Bjorn_and_John_-_Young_Folks.mp3");
//                "/arbeit/an_den_deutschen_mond/ich_stand_auf_hohem_berge.wav");

        player.play(path);
    }

    @Override
    protected void play(AudioInputStream stream)
            throws LineUnavailableException, IOException {

        final AudioFormat outFormat = getOutFormat(stream.getFormat());
        final Info info = new Info(SourceDataLine.class, outFormat);

        try (final SourceDataLine line =
                     (SourceDataLine) AudioSystem.getLine(info)) {

            if (line != null) {
                line.open(outFormat);
                line.start();
                stream(getAudioInputStream(outFormat, stream), line);
                line.drain();
                line.stop();
            }
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
        final byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }

}