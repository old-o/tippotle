package net.doepner.app.typepad.action;

import net.doepner.speech.Speaker;
import net.doepner.text.TextCoordinates;
import net.doepner.text.WordProvider;
import net.doepner.ui.IAction;

import static net.doepner.app.typepad.action.ActionEnum.SPEAK_WORD;
import static org.guppy4j.BaseUtil.exists;

public final class SpeakWord implements IAction {

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
        final String word = wordProvider.word(textCoordinates.textPosition());
        if (exists(word)) {
            speaker.speak(word);
        }
    }

    @Override
    public ActionEnum id() {
        return SPEAK_WORD;
    }

}
