package net.doepner.resources;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static net.doepner.file.MediaTypeEnum.image;

/**
 * Finds batches of images
 */
public class StdImageCollector implements ImageCollector {

    private final List<String> folders;
    private final ResourceCollector collector;
    private final Log log;

    public StdImageCollector(ResourceCollector collector, int imageCount,
                             LogProvider logProvider) {
        folders = new ArrayList<>(imageCount);
        for (int i = 0; i < imageCount; i++) {
            folders.add("p" + i);
        }
        this.collector = collector;
        log = logProvider.getLog(getClass());
    }

    @Override
    public Iterable<Image> getImages(String name) {
        final Collection<Image> images = new LinkedList<>();
        final Iterable<URL> urls = collector.findAll(image, name, folders);
        for (URL url : urls) {
            try {
                images.add(url == null ? null : ImageIO.read(url));
            } catch (IOException e) {
                log.error(e);
            }
        }
        return images;
    }

}
