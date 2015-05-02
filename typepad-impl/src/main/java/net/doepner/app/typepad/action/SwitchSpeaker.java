package net.doepner.app.typepad.action;

import net.doepner.speech.IterableSpeakers;
import net.doepner.ui.IAction;

import static net.doepner.app.typepad.action.ActionEnum.SWITCH_SPEAKER;

/**
 * Switches the current speaker to the next available speaker
 */
public final class SwitchSpeaker implements IAction {

    private final IterableSpeakers speakers;

    public SwitchSpeaker(IterableSpeakers speakers) {
        this.speakers = speakers;
    }

    @Override
    public void execute() {
        speakers.next();
    }

    @Override
    public ActionEnum id() {
        return SWITCH_SPEAKER;
    }

    @Override
    public String iconName() {
        return speakers.name();
    }
}
