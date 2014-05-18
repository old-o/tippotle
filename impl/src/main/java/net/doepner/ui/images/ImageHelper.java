package net.doepner.ui.images;

import net.doepner.file.PathHelper;
import net.doepner.ui.Images;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static net.doepner.file.MediaTypeEnum.image;
import static net.doepner.file.PathType.DIRECTORY;

/**
 * Helps with loading images
 *
 * @deprecated Use a ResourceFinder instead
 */
@Deprecated
public class ImageHelper implements Images {

    private final PathHelper fileHelper;
    private final Path imgDir;

    public ImageHelper(PathHelper fileHelper) {
        this.fileHelper = fileHelper;
        this.imgDir = fileHelper.findOrCreate("images", DIRECTORY);
    }

    @Override
    public Iterable<Image> getImages(String word) {
        final List<Image> images = new LinkedList<>();
        if (word == null || word.trim().isEmpty()) {
            return images;
        }
        final String name = word.toLowerCase();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(imgDir, "[0-9]")) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    final Image image = findImage(path, name);
                    images.add(image);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    private Image findImage(Path dir, String name) {
        /*
        final String[] split = name.split("[^\\w']+");
        if (split.length == 0) {
            return null;
        }
        final String imageName = split[split.length - 1];
        */

        final Path imagePath = fileHelper.findInDir(dir, name, image);

        if (imagePath != null) {
            try {
                return ImageIO.read(imagePath.toFile());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            return null;
        }
    }
}