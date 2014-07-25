package net.doepner.app.typepad.action;

import net.doepner.speech.IterableSpeakers;
import net.doepner.ui.IAction;

/**
 * Switches the current speaker to the next available speaker
 */
public class SwitchSpeaker implements IAction {

    private final IterableSpeakers speakers;

    public SwitchSpeaker(IterableSpeakers speakers) {
        this.speakers = speakers;
    }

    @Override
    public void actionPerformed() {
        speakers.nextSpeaker();
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SWITCH_SPEAKER;
    }

    @Override
    public String getIconName() {
        return speakers.getName();
    }
}
