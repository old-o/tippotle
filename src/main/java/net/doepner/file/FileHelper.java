package net.doepner.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileHelper implements TextBuffers {

	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

	private static final String USER_HOME =
            System.getProperty("user.home");

	private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

	private static final Path DIR = Paths.get(USER_HOME, ".typepad");

    @Override
	public void save(String text, int i) {
		try {
			Files.write(getFilePath(i), Arrays.asList(text), 
					DEFAULT_CHARSET);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

    @Override
	public String load(int i) {
		final StringBuilder result = new StringBuilder();
		try {
			for (String line : Files.readAllLines(getFilePath(i),
					DEFAULT_CHARSET)) {
				result.append(line).append(LINE_SEPARATOR);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return result.toString();
	}

	private Path getFilePath(int i) throws IOException {
		if (!Files.exists(DIR)) {
			Files.createDirectory(DIR);
		}
		final Path path = DIR.resolve(i + ".txt");

		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		return path;
	}
}
