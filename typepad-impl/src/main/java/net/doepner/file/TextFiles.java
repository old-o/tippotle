package net.doepner.file;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static net.doepner.file.PathType.FILE;
import static net.doepner.log.Log.Level.info;

/**
 * Implements text buffers using file system storage
 */
public class TextFiles implements TextBuffers {

    private final int maxBuffer;

    private final Log log;

    private final PathHelper pathHelper;

    private int currentBufferIndex;

    public TextFiles(LogProvider logProvider,
                     PathHelper pathHelper,
                     int maxBuffer) {
        this.maxBuffer = maxBuffer;
        log = logProvider.getLog(getClass());
        this.pathHelper = pathHelper;
    }

    @Override
    public void nextBuffer() {
        currentBufferIndex = currentBufferIndex % maxBuffer + 1;
    }

    @Override
    public void save(String text) {
        final Path buffer = getCurrentBuffer();
        final byte[] bytes = text.getBytes();
        try {
            write(buffer, bytes);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Saved buffer {}", buffer);
    }

    @Override
    public String load() {
        final Path buffer = getCurrentBuffer();
        final byte[] bytes;
        try {
            bytes = readAllBytes(buffer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Loaded buffer {}", buffer);
        return new String(bytes);
    }

    private Path getCurrentBuffer() {
        return pathHelper.findOrCreate(currentBufferIndex + ".txt", FILE);
    }
}
