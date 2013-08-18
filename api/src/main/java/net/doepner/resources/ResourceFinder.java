package net.doepner.resources;

import java.net.URL;

/**
 * Finds resources, like images or audio files
 */
public interface ResourceFinder {

    URL find(String name);
}
