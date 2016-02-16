package net.doepner.app.tippotle.action;

import net.doepner.speech.IterableSpeakers;
import net.doepner.ui.Action;

import static net.doepner.app.tippotle.action.ActionEnum.SWITCH_SPEAKER;

/**
 * Switches the current speaker to the next available speaker
 */
public final class SwitchSpeaker implements Action {

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
    public Object iconKey() {
        return speakers.name();
    }
}
