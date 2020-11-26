package org.oldo.ui;

import org.guppy4j.event.ChangeListener;
import org.oldo.i18n.L10n;
import org.oldo.lang.Language;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Simple delegating action wrapper
 */
public final class SwingAction extends AbstractAction implements ChangeListener<Language> {

    private static final long serialVersionUID = 1L;

    private final Action action;

    private final L10n<ActionId, String> descriptions;
    private final Icons icons;

    public SwingAction(Action action,
                       L10n<ActionId, String> descriptions,
                       Icons icons) {
        this.action = action;
        this.descriptions = descriptions;
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
        putValue(SHORT_DESCRIPTION, descriptions.get(action.id(), after));
        update();
    }

    private void update() {
        putValue(LARGE_ICON_KEY, icons.get(action.iconKey()));
        setEnabled(action.isEnabled());
    }
}
