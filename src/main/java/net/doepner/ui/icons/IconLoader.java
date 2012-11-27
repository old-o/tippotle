package net.doepner.ui.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.doepner.ui.action.Action;
import net.doepner.ui.action.ActionId;

/**
 * Loads icons from the classpath
 */
public class IconLoader {

    public static void setIcon(Action action) {
        setIcon(action, getIconFileName(action.getId()));
    }

    public static void setIcon(javax.swing.Action action, String fileName) {
        final Icon icon = IconLoader.load(fileName);
        if (icon != null) {
            action.putValue(Action.LARGE_ICON_KEY, icon);
        }
    }

    public static Icon load(String fileName) {
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
