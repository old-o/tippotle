package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static net.doepner.file.PathType.FILE;

/**
 * Implements text buffers using file system storage
 */
public class TextFiles implements TextBuffers {

    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private final PathHelper fileHelper;

    public TextFiles(PathHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Override
    public void save(String text, int i) {
        try {
            Files.write(getBuffer(i), Arrays.asList(text), DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String load(int i) {
        final StringBuilder result = new StringBuilder();
        try {
            for (String line : Files.readAllLines(getBuffer(i), DEFAULT_CHARSET)) {
                result.append(line).append(File.separator);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return result.toString();
    }

    private Path getBuffer(int i) throws IOException {
        return fileHelper.findOrCreate(i + ".txt", FILE);
    }
}
