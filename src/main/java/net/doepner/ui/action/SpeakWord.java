package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.speech.Speaker;
import net.doepner.text.TextCoordinates;
import net.doepner.text.WordProvider;

public class SpeakWord extends AbstractAction implements IdAction {

	private final WordProvider wordProvider;
	private final Speaker speaker;
    private final TextCoordinates coords;
	
	public SpeakWord(WordProvider wordProvider, TextCoordinates coords,
                     Speaker speaker) {
		this.wordProvider = wordProvider;
		this.speaker = speaker;
        this.coords = coords;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		speaker.speak(wordProvider.getWord(coords.getTextPosition()));
	}

    @Override
    public ActionId getId() {
        return ActionId.SPEAK_WORD;
    }
}
