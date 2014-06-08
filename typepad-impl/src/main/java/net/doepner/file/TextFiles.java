package net.doepner.file;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static net.doepner.file.PathType.FILE;
import static net.doepner.log.Log.Level.info;

/**
 * Implements text buffers using file system storage
 */
public class TextFiles implements TextBuffers {

    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private static final int MAX_BUFFER = 5;

    private final Log log;

    private final PathHelper fileHelper;

    private int currentBufferIndex;

    @Override
    public void nextBuffer() {
        currentBufferIndex = currentBufferIndex % MAX_BUFFER + 1;
    }

    public TextFiles(LogProvider logProvider, PathHelper fileHelper) {
        log = logProvider.getLog(getClass());
        this.fileHelper = fileHelper;
    }

    @Override
    public void save(String text) {
        final Path buffer = getCurrentBuffer();
        try {
            Files.write(buffer, Arrays.asList(text), DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Saved buffer {}", buffer);
    }

    @Override
    public String load() {
        final Path buffer = getCurrentBuffer();
        final StringBuilder result = new StringBuilder();
        try {
            for (String line : Files.readAllLines(buffer, DEFAULT_CHARSET)) {
                result.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.as(info, "Loaded buffer {}", buffer);
        return result.toString();
    }

    private Path getCurrentBuffer() {
        return fileHelper.findOrCreate(currentBufferIndex + ".txt", FILE);
    }
}
