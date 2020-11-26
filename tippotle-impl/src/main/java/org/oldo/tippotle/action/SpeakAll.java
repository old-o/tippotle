package org.oldo.tippotle.action;

import org.oldo.speech.Speaker;
import org.oldo.text.TextProvider;
import org.oldo.ui.Action;

/**
 * Lets the speaker read the whole text from the model
 */
public final class SpeakAll implements Action {

    private final TextProvider textProvider;
    private final Speaker speaker;

    public SpeakAll(TextProvider textProvider,
                    Speaker speaker) {
        this.textProvider = textProvider;
        this.speaker = speaker;
    }

    @Override
    public void execute() {
        final String text = textProvider.getText();
        if (text != null && !text.isEmpty()) {
            speaker.speak(text);
        }
    }

    @Override
    public ActionEnum id() {
        return ActionEnum.SPEAK_ALL;
    }
}
