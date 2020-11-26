package org.oldo.tippotle.ui;

import org.guppy4j.event.ChangeNotifier;
import org.oldo.i18n.L10n;
import org.oldo.lang.Language;
import org.oldo.ui.Action;
import org.oldo.ui.ActionId;
import org.oldo.ui.ActionTarget;
import org.oldo.ui.Icons;
import org.oldo.ui.SwingAction;

/**
 * Converts framework-agnostic actions to Swing actions
 */
public final class ActionConverter {

    private final ActionTarget actionTarget;

    private final L10n<ActionId, String> actionDescriptions;
    private final Icons icons;
    private final ChangeNotifier<Language> languageChanger;

    public ActionConverter(ActionTarget actionTarget,
                           L10n<ActionId, String> actionDescriptions,
                           Icons icons,
                           ChangeNotifier<Language> languageChanger) {
        this.actionDescriptions = actionDescriptions;
        this.actionTarget = actionTarget;
        this.icons = icons;
        this.languageChanger = languageChanger;
    }

    public SwingAction from(Action action) {
        final SwingAction swingAction = new SwingAction(action, actionDescriptions, icons);
        languageChanger.addListener(swingAction);
        actionTarget.addAction(swingAction);
        return swingAction;
    }
}
