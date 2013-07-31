package net.doepner.typepad;

import javax.swing.SwingUtilities;

import net.doepner.event.ChangeListener;
import net.doepner.lang.ILanguage;
import net.doepner.text.TextListener;
import net.doepner.typepad.action.ResizeFont;
import net.doepner.typepad.action.SpeakWord;
import net.doepner.typepad.action.SwitchBuffer;
import net.doepner.typepad.action.SwitchLanguage;
import net.doepner.typepad.action.SwitchSpeaker;
import net.doepner.ui.Editor;
import net.doepner.ui.Images;

/**
 * Application controller
 */
public class Controller {

    public Controller(final IModel model, final IView view, final IServices services) {
        final Editor editor = view.getEditor();

        view.setActions(new SwitchLanguage(model),
            new SpeakWord(model, editor, services.getSpeaker()),
            new ResizeFont(-1, editor), new ResizeFont(+1, editor),
            new SwitchBuffer(model, services),
            new SwitchSpeaker(services));

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
            final Images images = services.getImages();

            @Override
            public void handleChange(final Integer before, final Integer after) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        final char ch = model.getCharacter(after);
                        final String word = model.getWord(after);
                        view.showCharImages(images.getImages(String.valueOf(ch)));
                        view.showWordImages(images.getImages(word));
                    }
                });
            }
        });
    }
}