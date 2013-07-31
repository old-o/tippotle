package net.doepner.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

import net.doepner.file.PathHelper;
import net.doepner.lang.LanguageProvider;

import static net.doepner.file.PathType.DIRECTORY;

/**
 * TODO: Document this
 */
public class ResourceFinder {

    private final PathHelper pathHelper;
    private final String[] extensions;
    private final Path audioDir;

    private final LanguageProvider languageProvider;

    public ResourceFinder(PathHelper pathHelper, String baseDir,
                          LanguageProvider languageProvider,
                          String... extensions) {
        this.pathHelper = pathHelper;
        this.extensions = extensions;
        this.audioDir = pathHelper.findOrCreate(baseDir, DIRECTORY);
        this.languageProvider = languageProvider;
    }

    public URL find(String name) {
        final URL resource = fromClasspath(name, extensions);
        if (resource != null) {
            return resource;
        }
        try {
            final Path audioDir1 = audioDir.resolve(languageProvider.getLanguage().getCode());
            final Path path = pathHelper.findInDir(audioDir1, name, extensions);
            if (path != null) {
                return path.toUri().toURL();
            }
            if (name.contains(" ") || name.trim().length() < 2) {
                return null;
            }
            return getUrl(audioDir1, name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getUrl(Path audioDir1, String name) throws IOException {
        final URL url = new URL("http://translate.google.com/translate_tts?ie=UTF-8" +
                "&tl=" + languageProvider.getLanguage().getCode() +
                "&q=" + name.toLowerCase());

        final URLConnection c = url.openConnection();
        c.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        final ReadableByteChannel rbc = Channels.newChannel(c.getInputStream());
        final File file = audioDir1.resolve(name + ".mp3").toFile();

        try (final FileOutputStream fos = new FileOutputStream(file)) {
            final FileChannel channel = fos.getChannel();
            channel.transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        return file.toURI().toURL();
    }

    private URL fromClasspath(String name, String... extensions) {
        for (String extension : extensions) {
            final URL resource = getClass().getResource(name + extension);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }
}
