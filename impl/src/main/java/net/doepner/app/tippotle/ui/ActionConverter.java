package net.doepner.app.tippotle.ui;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.ui.Action;
import net.doepner.ui.ActionId;
import net.doepner.ui.ActionTarget;
import net.doepner.ui.Icons;
import net.doepner.ui.SwingAction;
import org.guppy4j.event.ChangeNotifier;

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
