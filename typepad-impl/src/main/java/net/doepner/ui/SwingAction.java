package net.doepner.ui;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.net.URL;

import static org.guppy4j.log.Log.Level.error;

/**
 * Simple delegating action wrapper
 */
public final class SwingAction extends AbstractAction implements UiAction {

    private static final long serialVersionUID = 1L;

    private final L10n<IAction, String> descriptions;
    private final IAction action;

    private final Log log;

    public SwingAction(IAction action,
                       L10n<IAction, String> descriptions,
                       LogProvider logProvider) {
        log = logProvider.getLog(getClass());
        this.action = action;
        this.descriptions = descriptions;
        updateIcon();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.execute();
        updateIcon();
    }

    @Override
    public void handleChange(Language before, Language after) {
        final String descr = descriptions.get(action, after);
        putValue(Action.SHORT_DESCRIPTION, descr);
        updateIcon();
    }

    private void updateIcon() {
        putValue(Action.LARGE_ICON_KEY, getIcon());
    }

    private Icon getIcon() {
        final String iconName = action.iconName();
        final String fileName = (iconName == null ? "unknown" : iconName) + ".png";
        final URL resource = getClass().getResource(fileName);
        if (resource == null) {
            log.as(error, "Icon image not found: {}", fileName);
            return null;
        } else {
            return new ImageIcon(resource);
        }
    }
}
