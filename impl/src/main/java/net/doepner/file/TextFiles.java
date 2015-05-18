package net.doepner.file;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static net.doepner.file.PathType.FILE;
import static org.guppy4j.log.Log.Level.info;

/**
 * Implements text buffers using file system storage
 */
public final class TextFiles implements TextBuffers {

    private final Log log;
    private final ApplicationFiles applicationFiles;

    public TextFiles(LogProvider logProvider,
                     ApplicationFiles applicationFiles) {
        log = logProvider.getLog(getClass());
        this.applicationFiles = applicationFiles;
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
        return applicationFiles.findOrCreate(i + ".txt", FILE);
    }
}
