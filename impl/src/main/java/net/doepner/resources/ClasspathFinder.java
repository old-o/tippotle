package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.MediaType;
import net.doepner.lang.Language;

import java.net.URL;

/**
 * Finds classpath resources
 */
public class ClasspathFinder implements ResourceFinder {

    private final String baseLocation;

    public ClasspathFinder(Package basePackage) {
        baseLocation = '/' + basePackage.getName().replace('.', '/');
    }

    @Override
    public URL find(MediaType mediaType, Language language, String category, String name) {
        final String location = baseLocation
                + '/' + mediaType.getGroupingName()
                + '/' + pathPart(language)
                + '/' + pathPart(category)
                + '/' + name;

        for (FileType fileType : mediaType.getFileTypes()) {
            final String filePath = location + '.' + fileType.getExtension();
            final URL resource = getClass().getResource(filePath);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }

    private String pathPart(String category) {
        return category != null ? category : UNSPECIFIED_PATH_PART;
    }

    private String pathPart(Language language) {
        return language != null ? language.getCode() : UNSPECIFIED_PATH_PART;
    }
}
