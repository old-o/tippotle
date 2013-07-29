package net.doepner.typepad;

import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;

import net.doepner.event.ChangeListener;
import net.doepner.lang.ILanguage;
import net.doepner.text.TextListener;
import net.doepner.typepad.action.ResizeFont;
import net.doepner.typepad.action.SpeakWord;
import net.doepner.typepad.action.SwitchBuffer;
import net.doepner.typepad.action.SwitchLanguage;
import net.doepner.ui.Editor;
import net.doepner.ui.IAction;

/**
 * Application controller
 */
public class Controller {

    public Controller(final IModel model, final IView view, final IServices services) {
        final Editor editor = view.getEditor();

        final Iterable<IAction> actions = new LinkedList<>(
                Arrays.asList(
                        new SwitchLanguage(model),
                        new SpeakWord(model, editor, services.getSpeaker()),
                        new ResizeFont(-1, editor), new ResizeFont(+1, editor),
                        new SwitchBuffer(model, services)));


        view.setActions(actions);
        view.setLanguage(model.getLanguage());

        model.addListener(new ChangeListener<ILanguage>() {
            @Override
            public void handleChange(ILanguage before, ILanguage after) {
                view.setLanguage(after);
                services.getLog().info("Language changed to: " + after);
            }
        });

        model.addTextListener(new TextListener() {
            @Override
            public void handleText(String text) {
                services.getSpeaker().speak(text);
            }
        });

        editor.addTextPositionListener(new ChangeListener<Integer>() {
            @Override
            public void handleChange(Integer before, Integer after) {
                final String word = model.getWord(after);
                final Image image = services.getImages().getImage(word);
                view.showImage(image);
            }
        });
    }
}
