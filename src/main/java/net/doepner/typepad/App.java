package net.doepner.typepad;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;

import net.doepner.ChangeListener;
import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.Language;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.text.WordExtractor;
import net.doepner.ui.action.Action;
import net.doepner.ui.action.ResizeFont;
import net.doepner.ui.action.SpeakWord;
import net.doepner.ui.action.SwitchBuffer;
import net.doepner.ui.action.SwitchLanguage;
import net.doepner.ui.l10n.ActionDescriptions;
import net.doepner.ui.text.UiTextModel;

import static net.doepner.typepad.DocUtil.prepareDocument;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    private final ActionDescriptions actionDescr =
            new ActionDescriptions();

    private final TypePad typePad;

    public App() {
        final LanguageChanger languageChanger = new EnglishOrGerman();
        final Speaker speaker = new ESpeaker(languageChanger);

        final DefaultStyledDocument doc = prepareDocument(speaker);

        final JTextPane pane = new JTextPane(doc);

        final List<Action> actions = getActions(
                languageChanger, speaker, pane);

        languageChanger.addListener(new ChangeListener() {
            @Override
            public void handleChange() {
                for (Action action : actions) {
                    final Language language = languageChanger.getLanguage();
                    action.putValue(Action.SHORT_DESCRIPTION,
                            actionDescr.get(language, action.getId()));
                }
            }
        });

        typePad = new TypePad(pane, actions);
    }

    private List<Action> getActions(LanguageChanger languageChanger,
                                    Speaker speaker, JTextPane pane) {

        final Action switchLangAction = new SwitchLanguage(
                languageChanger);

        final Action biggerFontAction = new ResizeFont(+1, pane);
        final Action smallerFontAction = new ResizeFont(-1, pane);

        final UiTextModel textModel = new UiTextModel(pane);

        final Action speakWordAction = new SpeakWord(
                new WordExtractor(textModel), speaker);

        final Action switchBufferAction = new SwitchBuffer(5,
                textModel, textModel);

        return Arrays.asList(
                switchLangAction, speakWordAction,
                smallerFontAction, biggerFontAction,
                switchBufferAction);
    }

    private void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                typePad.show();
            }
        });
    }

}
