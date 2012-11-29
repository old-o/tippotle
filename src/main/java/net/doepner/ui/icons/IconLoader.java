package net.doepner.ui.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.doepner.ui.action.ActionId;

/**
 * Loads icons from the classpath
 */
public class IconLoader {

    public static Icon getIcon(ActionId actionId) {
        return getIcon(getIconFileName(actionId));
    }

    public static Icon getIcon(String fileName) {
        final URL resource = IconLoader.class.getResource(fileName);
        return resource != null ? new ImageIcon(resource) : null;
    }

    private static String getIconFileName(ActionId id) {
        switch (id) {
            case BIGGER_FONT:
                return "zoom-in.png";
            case SMALLER_FONT:
                return "zoom-out.png";
            case SPEAK_WORD:
                return "volume.png";
            case SWITCH_BUFFER:
                return "buffers.png";
            case SWITCH_LANGUAGE:
                return "languages.png";
        }
        throw new IllegalArgumentException("Unknown action id: " + id);
    }

}
