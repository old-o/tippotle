package net.doepner.ui;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.doepner.i18n.L10n;
import net.doepner.lang.ILanguage;

/**
 * Loads icons from the classpath
 */
public class IconLoader implements L10n<ActionId, Icon> {

    public Icon get(ActionId actionId, ILanguage language) {
        return getIcon(getIconFileName(actionId, language));
    }

    private String getIconFileName(ActionId actionId, ILanguage language) {
        final String iconName = actionId.getIconName();
        return iconName == null ? language.getCode() + ".png" : iconName;
    }

    private Icon getIcon(String fileName) {
        final URL resource = getClass().getResource(fileName);
        return resource != null ? new ImageIcon(resource) : null;
    }
}
