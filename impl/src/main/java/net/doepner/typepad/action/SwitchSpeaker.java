package net.doepner.typepad.action;

import net.doepner.typepad.IServices;
import net.doepner.ui.IAction;

/**
 * Switches the current speaker to the next available speaker
 */
public class SwitchSpeaker implements IAction {

    private final IServices services;

    public SwitchSpeaker(IServices services) {
        this.services = services;
    }

    @Override
    public void actionPerformed() {
        services.switchSpeaker();
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SWITCH_SPEAKER;
    }

    @Override
    public String getIconName() {
        return services.getSpeaker().getName();
    }
}
