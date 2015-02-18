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
public final class TextFiles implements TextBuffers {

    private final Log log;
    private final PathHelper pathHelper;

    public TextFiles(LogProvider logProvider,
                     PathHelper pathHelper) {
        log = logProvider.getLog(getClass());
        this.pathHelper = pathHelper;
    }

    @Override
    public void save(String text, int i) {
        final Path buffer = getBuffer(i);
        final byte[] bytes = text.getBytes();
        try {
            write(buffer, bytes);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Saved buffer {}", buffer);
    }

    @Override
    public String load(int i) {
        final Path buffer = getBuffer(i);
        final byte[] bytes;
        try {
            bytes = readAllBytes(buffer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Loaded buffer {}", buffer);
        return new String(bytes);
    }

    private Path getBuffer(int i) {
        return pathHelper.findOrCreate(i + ".txt", FILE);
    }
}
