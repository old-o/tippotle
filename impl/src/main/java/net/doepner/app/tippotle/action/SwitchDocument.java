package net.doepner.app.tippotle.action;

import net.doepner.text.DocumentModel;
import net.doepner.ui.Action;

import static net.doepner.app.tippotle.action.ActionEnum.SWITCH_DOCUMENT;

/**
 * Switches to the next document
 */
public final class SwitchDocument implements Action {

    private final DocumentModel model;

    /**
     * @param model The document model that will perform the actual switching
     */
    public SwitchDocument(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.switchDocument();
    }

    @Override
    public ActionEnum id() {
        return SWITCH_DOCUMENT;
    }
}
