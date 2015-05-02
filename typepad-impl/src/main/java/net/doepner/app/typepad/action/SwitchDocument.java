package net.doepner.app.typepad.action;

import net.doepner.text.DocumentModel;
import net.doepner.ui.IAction;

import static net.doepner.app.typepad.action.ActionEnum.SWITCH_BUFFER;

/**
 * Switches to the next document
 */
public final class SwitchDocument implements IAction {

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
        return SWITCH_BUFFER;
    }
}
