package net.doepner.typepad.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import net.doepner.i18n.L10n;
import net.doepner.lang.ILanguage;
import net.doepner.ui.ActionId;
import net.doepner.ui.IAction;
import net.doepner.ui.UiAction;

/**
 * Simple delegating action wrapper
 */
public class SwingAction extends AbstractAction implements UiAction {

    private final IAction action;
    private final L10n<ActionId, String> descriptions;
    private final L10n<ActionId, Icon> icons;

    public SwingAction(IAction action,
                       L10n<ActionId, Icon> icons,
                       L10n<ActionId, String> descriptions) {
        this.action = action;
        this.icons = icons;
        this.descriptions = descriptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.actionPerformed();
    }

    @Override
    public void setLanguage(ILanguage language) {
        final String descr = descriptions.get(action.getId(), language);
        putValue(Action.SHORT_DESCRIPTION, descr);

        final Icon icon = icons.get(action.getId(), language);
        putValue(Action.LARGE_ICON_KEY, icon);
    }
}
