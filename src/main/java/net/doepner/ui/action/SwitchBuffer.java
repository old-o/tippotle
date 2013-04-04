package net.doepner.ui.action;

import net.doepner.app.api.IModel;
import net.doepner.app.api.IServices;

public class SwitchBuffer implements IAction {

    private final IModel model;
    private final IServices services;

    public SwitchBuffer(IModel model, IServices services) {
        this.model = model;
        this.services = services;
        loadText();
    }

    @Override
    public void actionPerformed() {
        services.saveBuffer(model);
        model.nextBuffer();
        loadText();
    }

    private void loadText() {
        services.loadBuffer(model);
    }

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_BUFFER;
    }
}
