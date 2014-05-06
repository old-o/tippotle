package net.doepner.app.typepad.action;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;
import net.doepner.ui.IAction;
import net.doepner.ui.UiAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;

import static net.doepner.log.Log.Level.error;

/**
 * Simple delegating action wrapper
 */
public class SwingAction extends AbstractAction implements UiAction {

    private final L10n<IAction, String> descriptions;
    private final IAction action;

    private final Log log;

    public SwingAction(IAction action,
                       L10n<IAction, String> descriptions,
                       LogProvider logProvider) {
        this.action = action;
        this.descriptions = descriptions;
        this.log = logProvider.getLog(getClass());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.actionPerformed();
        updateIcon();
    }

    @Override
    public void setLanguage(Language language) {
        final String descr = descriptions.get(action, language);
        putValue(Action.SHORT_DESCRIPTION, descr);
        updateIcon();
    }

    private void updateIcon() {
        final Icon icon = getIcon(action);
        putValue(Action.LARGE_ICON_KEY, icon);
    }

    private Icon getIcon(IAction action) {
        final String iconName = action.getIconName();
        final String fileName = (iconName == null ? "unknown" : iconName) + ".png";
        final URL resource = getClass().getResource(fileName);
        if (resource == null) {
            log.$(error, "Icon image not found: {}", fileName);
            return null;
        } else {
            return new ImageIcon(resource);
        }
    }
}
