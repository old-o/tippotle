package net.doepner.resources;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static net.doepner.file.MediaTypeEnum.image;
import static org.guppy4j.log.Log.Level.error;

/**
 * Finds batches of images
 */
public final class StdImageCollector implements Function<String, Iterable<Image>> {

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
    public Iterable<Image> apply(String name) {
        final Collection<Image> images = new LinkedList<>();
        for (URL url : collector.findAll(image, name, folders)) {
            try {
                images.add(url == null ? null : ImageIO.read(url));
            } catch (IOException e) {
                log.as(error, e);
            }
        }
        return images;
    }

}
