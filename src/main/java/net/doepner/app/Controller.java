package net.doepner.app;

import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;

import net.doepner.app.api.IModel;
import net.doepner.app.api.IServices;
import net.doepner.app.api.IView;
import net.doepner.event.ChangeListener;
import net.doepner.ui.IconL10nUpdater;
import net.doepner.ui.action.IdAction;
import net.doepner.ui.action.ResizeFont;
import net.doepner.ui.action.SpeakWord;
import net.doepner.ui.action.SwitchBuffer;
import net.doepner.ui.action.SwitchLanguage;

/**
 * Application controller
 */
public class Controller {

    public Controller(final IModel model, final IView view,
                      final IServices services) {

        final Iterable<IdAction> actions = new LinkedList<IdAction>(
                Arrays.asList(
                        new SwitchLanguage(model),
                        new SpeakWord(model, view, services.getSpeaker()),
                        new ResizeFont(-1, view), new ResizeFont(+1, view),
                        new SwitchBuffer(model, services)));

        model.addListener(new IconL10nUpdater(actions));

        view.setActions(actions);

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
