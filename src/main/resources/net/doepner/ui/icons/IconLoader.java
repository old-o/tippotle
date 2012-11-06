package net.doepner.ui.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Loads icons from the classpath
 */
public class IconLoader {

    public static Icon load(String fileName) {
        final URL resource = IconLoader.class.getResource(fileName);
        return resource != null ? new ImageIcon(resource) : null;
    }

}
