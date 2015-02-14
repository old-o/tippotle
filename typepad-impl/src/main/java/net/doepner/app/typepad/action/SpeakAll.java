package net.doepner.app.typepad.action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;
import net.doepner.ui.IAction;

/**
 * Lets the speaker read the whole text from the model
 */
public class SpeakAll implements IAction {

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
    public ActionEnum getId() {
        return ActionEnum.SPEAK_ALL;
    }
}
