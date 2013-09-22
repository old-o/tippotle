package net.doepner.app.typepad;

import net.doepner.app.typepad.action.ResizeFont;
import net.doepner.app.typepad.action.SpeakWord;
import net.doepner.app.typepad.action.SwitchBuffer;
import net.doepner.app.typepad.action.SwitchLanguage;
import net.doepner.app.typepad.action.SwitchSpeaker;
import net.doepner.event.ChangeListener;
import net.doepner.lang.Language;
import net.doepner.text.TextListener;
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

        model.addListener(new ChangeListener<Language>() {
            @Override
            public void handleChange(Language before, Language after) {
                view.setLanguage(after);
                services.getLog().info("Language changed to: " + after);
            }
        });

        model.addTextListener(new TextListener() {
            @Override
            public void handleText(final String text) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        services.getSpeaker().speak(text);
                    }
                }).start();
            }
        });

        editor.addTextPositionListener(new ChangeListener<Integer>() {
            final Images images = services.getImages();

            @Override
            public void handleChange(final Integer before, final Integer after) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final char ch = model.getCharacter(after);
                        final String word = model.getWord(after);
                        view.showCharImages(images.getImages(String.valueOf(ch)));
                        view.showWordImages(images.getImages(word));
                    }
                }).start();
            }
        });
    }
}