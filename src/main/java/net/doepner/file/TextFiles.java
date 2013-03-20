package net.doepner.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * TODO: Document this
 */
public class TextFiles implements TextBuffers {

    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    private final FileHelper fileHelper;

    public TextFiles(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Override
    public void save(String text, int i) {
        try {
            Files.write(getBuffer(i), Arrays.asList(text),
                    DEFAULT_CHARSET);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String load(int i) {
        final StringBuilder result = new StringBuilder();
        try {
            for (String line : Files.readAllLines(getBuffer(i),
                    DEFAULT_CHARSET)) {
                result.append(line).append(LINE_SEPARATOR);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return result.toString();
    }

    private Path getBuffer(int i) throws IOException {
        return fileHelper.getAppFilePath(i + ".txt");
    }
}
