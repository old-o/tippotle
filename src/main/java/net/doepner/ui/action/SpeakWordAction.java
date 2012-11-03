package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;
import net.doepner.ui.Icons;

public class SpeakWordAction extends AbstractAction {

	private final TextProvider textProvider;
	private final Speaker speaker;
	
	public SpeakWordAction(TextProvider textProvider, Speaker speaker) {
		this.textProvider = textProvider;
		this.speaker = speaker;
		putValue(Action.SHORT_DESCRIPTION, "Read current word");
		Icons.setLargeIcon(this, "volume");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		speaker.speak(textProvider.getText());
	}
}
