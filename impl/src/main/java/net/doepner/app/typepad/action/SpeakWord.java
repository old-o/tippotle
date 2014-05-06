package net.doepner.app.typepad.action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextCoordinates;
import net.doepner.text.WordProvider;
import net.doepner.ui.IAction;

public class SpeakWord implements IAction {

    private final TextCoordinates textCoordinates;
    private final WordProvider wordProvider;
    private final Speaker speaker;

    public SpeakWord(TextCoordinates textCoordinates,
                     WordProvider wordProvider,
                     Speaker speaker) {
        this.textCoordinates = textCoordinates;
        this.wordProvider = wordProvider;
        this.speaker = speaker;
    }

    @Override
    public void actionPerformed() {
        final String word = wordProvider.getWord(textCoordinates.getTextPosition());
        if (word != null && !word.isEmpty()) {
            speaker.speak(word);
        }
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
