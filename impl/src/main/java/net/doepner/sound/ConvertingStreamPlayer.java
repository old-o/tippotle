package net.doepner.sound;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class ConvertingStreamPlayer implements AudioStreamPlayer {

    @Override
    public boolean isPlaybackBlockingThread() {
        return false;
    }

    @Override
    public void play(final AudioInputStream stream)
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
        final byte[] buffer = new byte[8192];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}