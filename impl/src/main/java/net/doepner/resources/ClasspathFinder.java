package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.MediaType;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.lang.invoke.MethodHandles;
import java.net.URL;

import static net.doepner.log.Log.Level.debug;
import static net.doepner.log.Log.Level.info;

/**
 * Finds classpath resources
 */
public class ClasspathFinder implements ResourceFinder {

    private final String baseLocation;
    private final Log log;

    public ClasspathFinder(LogProvider logProvider) {
        this(logProvider, MethodHandles.lookup().lookupClass().getPackage());
    }

    public ClasspathFinder(LogProvider logProvider, Package basePackage) {
        baseLocation = getLocation(basePackage);
        log = logProvider.getLog(getClass());
    }

    private String getLocation(Package pkg) {
        return '/' + pkg.getName().replace('.', '/');
    }

    @Override
    public URL find(MediaType mediaType, String name, Language language, String category) {

        final String location = baseLocation
                + '/' + mediaType.getGroupingName()
                + '/' + pathPart(language)
                + '/' + pathPart(category)
                + '/' + name;

        for (FileType fileType : mediaType.getFileTypes()) {
            final String fileLocation = location + '.' + fileType.getExtension();
            final URL resource = getClass().getResource(fileLocation);
            if (resource != null) {
                log.$(info, "Found classpath resource: {}", fileLocation);
                return resource;
            } else {
                log.$(debug, "Classpath resource not found: {}", fileLocation);
            }
        }

        return null;
    }

    private String pathPart(String category) {
        return category == null ? "_" : category;
    }

    private String pathPart(Language language) {
        return language == null ? "_" : language.getCode();
    }
}
