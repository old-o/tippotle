package net.doepner.ui;

import javax.swing.Action;

import net.doepner.event.ChangeListener;
import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.ui.action.ActionId;
import net.doepner.ui.action.IdAction;
import net.doepner.ui.i18n.ActionDescriptions;

/**
 * Adjusts icon texts when language is changed
 */
public class IconL10nUpdater implements ChangeListener<Language> {

    private final L10n<ActionId> actionDescr = new ActionDescriptions();

    private Iterable<IdAction> actions;

    public IconL10nUpdater(Iterable<IdAction> actions) {
        this.actions = actions;
    }

    @Override
    public void handleChange(Language before, Language after) {
        for (IdAction action : actions) {
            final String descr = actionDescr.get(after, action.getId());
            action.putValue(Action.SHORT_DESCRIPTION, descr);
        }
    }
}
