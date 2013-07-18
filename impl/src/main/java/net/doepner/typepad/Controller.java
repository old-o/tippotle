package net.doepner.typepad;

import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;

import net.doepner.event.ChangeListener;
import net.doepner.lang.ILanguage;
import net.doepner.log.Log;
import net.doepner.typepad.action.ResizeFont;
import net.doepner.typepad.action.SpeakWord;
import net.doepner.typepad.action.SwitchBuffer;
import net.doepner.typepad.action.SwitchLanguage;
import net.doepner.ui.IAction;

/**
 * Application controller
 */
public class Controller {

    public Controller(final IModel model, final IView view,
                      final IServices services, final Log log) {

        final Iterable<IAction> actions = new LinkedList<>(
            Arrays.asList(
                new SwitchLanguage(model),
                new SpeakWord(model, view, services.getSpeaker()),
                new ResizeFont(-1, view), new ResizeFont(+1, view),
                new SwitchBuffer(model, services)));


        view.setActions(actions);
        view.setLanguage(model.getLanguage());

        model.addListener(new ChangeListener<ILanguage>() {
            @Override
            public void handleChange(ILanguage before, ILanguage after) {
                view.setLanguage(after);
                log.info("Language changed to: " + after);
            }
        });

        view.addTextPositionListener(new ChangeListener<Integer>() {
            @Override
            public void handleChange(Integer before, Integer after) {
                final String word = model.getWord(after);
                final Image image = services.getImageMap().getImage(word);
                view.showImage(image);
            }
        });
    }
}
