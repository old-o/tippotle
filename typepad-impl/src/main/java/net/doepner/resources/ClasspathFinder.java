package net.doepner.resources;

import net.doepner.file.FileType;
import net.doepner.file.MediaType;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static net.doepner.log.Log.Level.debug;
import static net.doepner.log.Log.Level.info;

/**
 * Finds classpath resources
 */
public final class ClasspathFinder implements ResourceFinder {

    private final String baseLocation;
    private final Log log;

    private final ConcurrentMap<String, URL> map = new ConcurrentHashMap<>();
    private final Set<String> nonExistent = new ConcurrentSkipListSet<>();

    public ClasspathFinder(LogProvider logProvider) {
        this(logProvider, MethodHandles.lookup().lookupClass().getPackage());
    }

    public ClasspathFinder(LogProvider logProvider, Package basePackage) {
        baseLocation = getLocation(basePackage);
        log = logProvider.getLog(getClass());
    }

    private static String getLocation(Package pkg) {
        return '/' + pkg.getName().replace('.', '/');
    }

    @Override
    public URL find(MediaType mediaType, String name, Language language, String category) {

        final String location = baseLocation
                + '/' + mediaType.getGroupingName()
                + '/' + pathPart(language)
                + '/' + pathPart(category)
                + '/' + name;

        if (nonExistent.contains(location)) {
            return null;
        }
        final URL cachedUrl = map.get(location);
        if (cachedUrl != null) {
            return cachedUrl;
        } else {
            final URL url = getUrl(mediaType, location);
            if (url == null) {
                nonExistent.add(location);
            } else {
                map.put(location, url);
            }
            return url;
        }
    }

    private URL getUrl(MediaType mediaType, String location) {
        for (FileType fileType : mediaType.getFileTypes()) {
            final String fileLocation = location + '.' + fileType.getExtension();
            final URL resource = getClass().getResource(fileLocation);
            if (resource != null) {
                log.as(info, "Found classpath resource: {}", fileLocation);
                return resource;
            } else {
                log.as(debug, "Classpath resource not found: {}", fileLocation);
            }
        }
        return null;
    }

    private static String pathPart(String category) {
        return category == null ? "_" : category;
    }

    private static String pathPart(Language language) {
        return language == null ? "_" : language.getCode();
    }
}
