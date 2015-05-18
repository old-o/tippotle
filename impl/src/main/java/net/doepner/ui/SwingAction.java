package net.doepner.ui;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import org.guppy4j.event.ChangeListener;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Simple delegating action wrapper
 */
public final class SwingAction extends AbstractAction implements ChangeListener<Language> {

    private static final long serialVersionUID = 1L;

    private final Action action;
    private final L10n<ActionId, String> actionDescriptions;
    private final Icons icons;

    public SwingAction(Action action,
                       L10n<ActionId, String> actionDescriptions,
                       Icons icons) {
        this.action = action;
        this.actionDescriptions = actionDescriptions;
        this.icons = icons;
        update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.execute();
        update();
    }

    @Override
    public void handleChange(Language before, Language after) {
        putValue(javax.swing.Action.SHORT_DESCRIPTION, actionDescriptions.get(action.id(), after));
        update();
    }

    private void update() {
        putValue(javax.swing.Action.LARGE_ICON_KEY, icons.get(action.iconKey()));
        setEnabled(action.isEnabled());
    }
}
