package net.doepner.app.typepad.action;

import net.doepner.speech.IterableSpeakers;
import net.doepner.ui.ActionId;
import net.doepner.ui.IAction;

import static net.doepner.ui.SwingUtil.doInBackground;

/**
 * TODO: Document this!
 */
public final class StopAudioAction implements IAction {

    private final IterableSpeakers speakers;

    public StopAudioAction(IterableSpeakers speakers) {
        this.speakers = speakers;
    }

    @Override
    public void execute() {
        doInBackground(speakers::stopAll);
    }

    @Override
    public ActionId getId() {
        return ActionEnum.STOP_AUDIO;
    }
}
