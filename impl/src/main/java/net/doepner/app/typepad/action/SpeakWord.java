package net.doepner.app.typepad.action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextCoordinates;
import net.doepner.text.WordProvider;
import net.doepner.ui.IAction;

public class SpeakWord implements IAction {

    private final WordProvider wordProvider;
    private final Speaker speaker;
    private final TextCoordinates coords;

    public SpeakWord(WordProvider wordProvider, TextCoordinates coords, Speaker speaker) {
        this.wordProvider = wordProvider;
        this.speaker = speaker;
        this.coords = coords;
    }

    @Override
    public void actionPerformed() {
        speaker.speak(wordProvider.getWord(coords.getTextPosition()));
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SPEAK_WORD;
    }

    @Override
    public String getIconName() {
        return getId().getIconName();
    }
}
