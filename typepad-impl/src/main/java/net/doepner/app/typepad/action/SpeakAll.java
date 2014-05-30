package net.doepner.app.typepad.action;

import net.doepner.app.typepad.IModel;
import net.doepner.speech.Speaker;
import net.doepner.ui.IAction;

/**
 * Lets the speaker read the whole text from the model
 */
public class SpeakAll implements IAction {

    private final IModel model;
    private final Speaker speaker;

    public SpeakAll(IModel model, Speaker speaker) {
        this.model = model;
        this.speaker = speaker;
    }

    @Override
    public void actionPerformed() {
        final String text = model.getText();
        if (text != null && !text.isEmpty()) {
            speaker.speak(text);
        }
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SPEAK_ALL;
    }

    @Override
    public String getIconName() {
        return getId().getIconName();
    }
}
