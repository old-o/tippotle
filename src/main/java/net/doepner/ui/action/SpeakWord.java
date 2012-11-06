package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;

public class SpeakWord extends AbstractAction implements Action {

	private final TextProvider textProvider;
	private final Speaker speaker;
	
	public SpeakWord(TextProvider textProvider, Speaker speaker) {
		this.textProvider = textProvider;
		this.speaker = speaker;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		speaker.speak(textProvider.getText());
	}

    @Override
    public ActionId getId() {
        return ActionId.SPEAK_WORD;
    }
}
