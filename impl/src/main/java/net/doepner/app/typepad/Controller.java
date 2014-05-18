package net.doepner.app.typepad;

import net.doepner.app.typepad.action.EmailAction;
import net.doepner.app.typepad.action.ResizeFont;
import net.doepner.app.typepad.action.SpeakAll;
import net.doepner.app.typepad.action.SpeakWord;
import net.doepner.app.typepad.action.SwitchBuffer;
import net.doepner.app.typepad.action.SwitchLanguage;
import net.doepner.app.typepad.action.SwitchSpeaker;
import net.doepner.event.ChangeListener;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;
import net.doepner.speech.Speaker;
import net.doepner.text.TextListener;
import net.doepner.ui.Editor;
import net.doepner.ui.Images;

import static net.doepner.log.Log.Level.info;
import static net.doepner.util.ComparisonUtil.bothNullOrEqual;
import static net.doepner.util.ComparisonUtil.not;

/**
 * Application controller
 */
public class Controller {

    private final Log log;

    public Controller(final IModel model,
                      final IView view,
                      final IServices services,
                      final LogProvider logProvider) {

        log = logProvider.getLog(getClass());

        final Editor editor = view.getEditor();
        final Speaker speaker = services.getSpeaker();

        view.setActions(
                new SwitchLanguage(model),
                new SpeakWord(editor, model, speaker),
                new ResizeFont(-1, editor),
                new ResizeFont(+1, editor),
                new SwitchBuffer(model, services),
                new SwitchSpeaker(services),
                new EmailAction(view.getEmailDialog(), model, services),
                new SpeakAll(model, speaker));

        view.setLanguage(model.getLanguage());

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                services.saveBuffer(model);
            }
        }));

        model.addListener(new ChangeListener<Language>() {
            @Override
            public void handleChange(Language before, Language after) {
                view.setLanguage(after);
                log.$(info, "Language changed from {} to {}", before, after);
            }
        });

        model.addTextListener(new TextListener() {
            @Override
            public void handleText(final String text) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        speaker.speak(text);
                    }
                }).start();
            }
        });

        editor.addTextPositionListener(new ChangeListener<Integer>() {
            @Override
            public void handleChange(final Integer before, final Integer after) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handleTestPositionChange(model, view, services, before, after);
                    }
                }).start();
            }
        });
    }

    private void handleTestPositionChange(IModel model, IView view, IServices services,
                                          Integer before, Integer after) {
        final Images images = services.getImages();

        final Character ch = model.getCharacter(after);
        final String word = model.getWord(after);

        if (not(bothNullOrEqual(ch, model.getCharacter(before)))) {
            view.showCharImages(images.getImages(String.valueOf(ch)));
            log.$(info, "Current character: {}", ch);
        }
        if (not(bothNullOrEqual(word, model.getWord(before)))) {
            view.showWordImages(images.getImages(word));
            log.$(info, "Current word: {}", word);
        }
    }
}