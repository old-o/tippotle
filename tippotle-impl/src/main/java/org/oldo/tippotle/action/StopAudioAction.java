package org.oldo.tippotle.action;

import org.oldo.speech.IterableSpeakers;
import org.oldo.ui.Action;

import static org.oldo.tippotle.action.ActionEnum.STOP_AUDIO;
import static org.oldo.ui.SwingUtil.doInBackground;

/**
 * Stops all current audio output
 */
public final class StopAudioAction implements Action {

    private final IterableSpeakers speakers;

    public StopAudioAction(IterableSpeakers speakers) {
        this.speakers = speakers;
    }

    @Override
    public void execute() {
        doInBackground(speakers::stopAll);
    }

    @Override
    public ActionEnum id() {
        return STOP_AUDIO;
    }
}
