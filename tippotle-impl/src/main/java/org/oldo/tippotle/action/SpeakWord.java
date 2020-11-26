package org.oldo.tippotle.action;

import org.oldo.speech.Speaker;
import org.oldo.text.TextCoordinates;
import org.oldo.text.WordProvider;
import org.oldo.ui.Action;

import static org.guppy4j.BaseUtil.exists;
import static org.oldo.tippotle.action.ActionEnum.SPEAK_WORD;

public final class SpeakWord implements Action {

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
    public void execute() {
        final String word = wordProvider.getWord(textCoordinates.textPosition()).getContent();
        if (exists(word)) {
            speaker.speak(word);
        }
    }

    @Override
    public ActionEnum id() {
        return SPEAK_WORD;
    }

}
