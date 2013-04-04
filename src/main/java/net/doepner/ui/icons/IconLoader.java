package net.doepner.ui.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.ui.action.ActionId;

/**
 * Loads icons from the classpath
 */
public class IconLoader implements L10n<ActionId, Icon> {

    public Icon get(ActionId actionId, Language language) {
        return getIcon(getIconFileName(actionId, language));
    }

    private String getIconFileName(ActionId actionId, Language language) {
        if (ActionId.SWITCH_LANGUAGE == actionId) {
            return language.getCode() + ".png";
        } else {
            return getIconFileName(actionId);
        }
    }

    private String getIconFileName(ActionId id) {
        switch (id) {
            case BIGGER_FONT:
                return "zoom-in.png";
            case SMALLER_FONT:
                return "zoom-out.png";
            case SPEAK_WORD:
                return "volume.png";
            case SWITCH_BUFFER:
                return "buffers.png";
        }
        throw new IllegalArgumentException("Unknown action id: " + id);
    }

    private Icon getIcon(String fileName) {
        final URL resource = getClass().getResource(fileName);
        return resource != null ? new ImageIcon(resource) : null;
    }
}
