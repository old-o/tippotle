package net.doepner.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;

/**
 * Detects the mime type of files (ideally based on marker in file content)
 */
public class FileTypeDetector extends java.nio.file.spi.FileTypeDetector {

    public static void main(String[] args) throws IOException {
//        final String filePath = "/home/oliver/.typepad/incoming/zeitgeist_mp3";
        final String filePath = "C:/Documents and Settings/isdc858/Desktop/example.ogg";

        // http://jeszysblog.wordpress.com/2012/03/05/file-type-detection-with-apache-tika/#comment-81
        final String mediaType = new Tika().detect(new File(filePath));
        System.out.println("tika says: " + mediaType);

        final Path path = Paths.get(filePath);
        final String contentType = Files.probeContentType(path);
        System.out.println("jdk says: " + contentType);
    }

    @Override
    public String probeContentType(Path path) throws IOException {
        return new Tika().detect(path.toFile());
    }
}
