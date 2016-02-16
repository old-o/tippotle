package net.doepner.app.tippotle.action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;
import net.doepner.ui.Action;

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
