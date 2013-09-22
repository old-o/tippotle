package net.doepner.app.typepad.action;

import net.doepner.app.typepad.IModel;
import net.doepner.app.typepad.IServices;
import net.doepner.ui.IAction;

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
    public ActionEnum getId() {
        return ActionEnum.SWITCH_BUFFER;
    }

    @Override
    public String getIconName() {
        return getId().getIconName();
    }
}
